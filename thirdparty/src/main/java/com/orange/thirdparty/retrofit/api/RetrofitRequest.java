package com.orange.thirdparty.retrofit.api;

import com.orange.lib.common.adapterpattern.CallbackAdapter;
import com.orange.lib.mvp.model.net.request.IRequest;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.mvp.model.net.request.request.NetRequestParams;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.utils.ReflectionUtils;
import com.orange.lib.utils.base.Preconditions;
import com.orange.thirdparty.retrofit.IRetrofitApi;
import com.orange.thirdparty.retrofit.RetrofitClient;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.rxjava.LoadingResponseBodyObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class RetrofitRequest implements IRequest {
    private AtomicBoolean mAlreadyStart;//网络请求开始
    private CountDownLatch mCompleteCountDown;//网络请求结束

    public static RetrofitRequest netInstance() {
        return new RetrofitRequest();
    }

    private LoadingResponseBodyObserver mLoadingResponseBodyObserver;

    private RetrofitRequest() {
        mLoadingResponseBodyObserver = LoadingResponseBodyObserver.newInstance();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 网络请求实现
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public <T> INetCancel request(NetRequestParams<T> netRequestParams) {
        if (Preconditions.isNull(netRequestParams)) return null;
        NetRequestParams.Method method = netRequestParams.getMethod();
        if (Preconditions.isNull(method)) method = NetRequestParams.Method.POST;
        String url = netRequestParams.getUrl();
        Map<String, String> params = netRequestParams.getParams();
        Map<String, String> headers = netRequestParams.getHeaders();
        ICallback<T> netCallback = netRequestParams.getNetCallback();
        if (NetRequestParams.Method.GET == method)
            return get(url, params, headers, netCallback);
        return post(url, params, headers, netCallback);
    }

    @Override
    public <T extends NetRequestParams<K>, K> INetCancel parallel(T... netRequests) {
        if (Preconditions.isNulls(netRequests)) return null;
        mAlreadyStart = new AtomicBoolean(false);
        int len = netRequests.length;
        mCompleteCountDown = new CountDownLatch(len);
        List<INetCancel> netCancels = new ArrayList<>();
        for (NetRequestParams<K> request : netRequests) {
            if (Preconditions.isNull(request)) {
                if (!Preconditions.isNull(mCompleteCountDown))
                    mCompleteCountDown.countDown();
                continue;
            }
            request.setNetCallback(wrapNetCallback(request.getNetCallback()));
            netCancels.add(request(request));
        }
        return wrapNetCancel(netCancels);
    }

    @Override
    public <T extends NetRequestParams<K>, K> INetCancel serial(T... netRequests) {
        if (Preconditions.isNulls(netRequests)) return null;
        mAlreadyStart = new AtomicBoolean(false);
        int len = netRequests.length;
        mCompleteCountDown = new CountDownLatch(len);
        LinkedList<T> netCancels = new LinkedList<T>(Arrays.asList(netRequests));
        T request = netCancels.pop();
        if (Preconditions.isNull(request)) {
            if (!Preconditions.isNull(mCompleteCountDown))
                mCompleteCountDown.countDown();
            continue;
        }
        request.setNetCallback(wrapNetCallback(request.getNetCallback()));
        netCancels.add(request(request));
        for (NetRequestParams<K> request : netRequests) {
            if (Preconditions.isNull(request)) {
                if (!Preconditions.isNull(mCompleteCountDown))
                    mCompleteCountDown.countDown();
                continue;
            }
            request.setNetCallback(wrapNetCallback(request.getNetCallback()));
            netCancels.add(request(request));
        }
        return wrapNetCancel(netCancels);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 工具方法
    ///////////////////////////////////////////////////////////////////////////
    private INetCancel wrapNetCancel(List<INetCancel> netCancels) {
        if (Preconditions.isEmpty(netCancels)) return null;
        return new INetCancel() {
            @Override
            public void cancel() {
                for (INetCancel netCancel : netCancels) {
                    if (Preconditions.isNull(netCancel)) continue;
                    netCancel.cancel();
                }
            }
        };
    }

    private <T> ICallback<T> wrapNetCallback(ICallback<T> netCallback) {
        if (Preconditions.isNull(netCallback)) {
            if (!Preconditions.isNull(mCompleteCountDown))
                mCompleteCountDown.countDown();
            return null;
        }
        ICallback<T> callback = new CallbackAdapter(netCallback) {
            @Override
            public void onStart() {
                if (Preconditions.isNull(mAlreadyStart) || mAlreadyStart.compareAndSet(false, true))
                    super.onStart();
            }

            @Override
            public void onComplete() {
                if (Preconditions.isNull(mCompleteCountDown))
                    super.onComplete();
                mCompleteCountDown.countDown();
                if (0 == mCompleteCountDown.getCount())
                    super.onComplete();
            }
        };
        ReflectionUtils.getGenericSuperclassActualTypeArg(callback.getClass());
        return callback;
    }

    /**
     * loading方式post网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param headers  请求头
     * @param callback 回调
     */
    public <T> INetCancel post(String url, Map<String, String> params, Map<String, String> headers, ICallback<T> callback) {
        if (Preconditions.isNull(mLoadingResponseBodyObserver)) return null;
        Observable<ResponseBody> observable = null;
        IRetrofitApi api = RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class);
        if (Preconditions.isEmpty(headers) && Preconditions.isEmpty(params)) {
            observable = api.post(url);
        } else if (Preconditions.isEmpty(headers)) {
            observable = api.post(url, params);
        } else {
            observable = api.post(url, params, headers);
        }
        return new RetrofitNetCancel(mLoadingResponseBodyObserver.subscribe(observable, callback));
    }

    /**
     * loading方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    public <T> INetCancel get(String url, Map<String, String> params, Map<String, String> headers, ICallback<T> callback) {
        if (Preconditions.isNull(mLoadingResponseBodyObserver)) return null;
        Observable<ResponseBody> observable = null;
        IRetrofitApi api = RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class);
        if (Preconditions.isEmpty(headers) && Preconditions.isEmpty(params)) {
            observable = api.get(url);
        } else if (Preconditions.isEmpty(headers)) {
            observable = api.get(url, params);
        } else {
            observable = api.get(url, params, headers);
        }
        return new RetrofitNetCancel(mLoadingResponseBodyObserver.subscribe(observable, callback));
    }
}
