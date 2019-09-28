package com.orange.thirdparty.retrofit.api;

import com.orange.lib.common.adapterpattern.NetCallbackAdapter;
import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.loading.callback.INetCallback;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.utils.ReflectionUtils;
import com.orange.lib.utils.base.Preconditions;
import com.orange.thirdparty.retrofit.IRetrofitCommonApi;
import com.orange.thirdparty.retrofit.RetrofitClient;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.rxjava.LoadingResponseBodyObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class RetrofitUrlApi implements IUrlApi {
    private AtomicBoolean mAlreadyStart;//网络请求开始
    private CountDownLatch mCompleteCountDown;//网络请求结束

    public static RetrofitUrlApi netInstance() {
        return new RetrofitUrlApi();
    }

    private LoadingResponseBodyObserver mLoadingResponseBodyObserver;

    private RetrofitUrlApi() {
        mLoadingResponseBodyObserver = LoadingResponseBodyObserver.newInstance();
    }

    /**
     * 网络请求
     *
     * @param netRequest 网络请求封装
     * @param <T>
     * @return
     */
    @Override
    public <T> INetCancel singleRequest(NetRequest<T> netRequest) {
        if (Preconditions.isNull(netRequest)) return null;
        NetRequest.Method method = netRequest.getMethod();
        if (Preconditions.isNull(method)) method = NetRequest.Method.POST;
        String url = netRequest.getUrl();
        Map<String, String> params = netRequest.getParams();
        Map<String, String> headers = netRequest.getHeaders();
        INetCallback<T> netCallback = netRequest.getNetCallback();
        if (NetRequest.Method.GET == method)
            return get(url, params, headers, netCallback);
        return post(url, params, headers, netCallback);
    }

    /**
     * 网络请求
     *
     * @param netRequest 网络请求封装
     * @return
     */
    @Override
    public <T extends NetRequest<K>, K> INetCancel request(T... netRequest) {
        if (Preconditions.isNulls(netRequest)) return null;
        mAlreadyStart = new AtomicBoolean(false);
        int len = netRequest.length;
        mCompleteCountDown = new CountDownLatch(len);
        List<INetCancel> netCancels = new ArrayList<>();
        for (NetRequest<K> request : netRequest) {
            if (Preconditions.isNull(request)) {
                if (!Preconditions.isNull(mCompleteCountDown))
                    mCompleteCountDown.countDown();
                continue;
            }
            request.setNetCallback(wrapNetCallback(request.getNetCallback()));
            netCancels.add(singleRequest(request));
        }
        return wrapNetCancel(netCancels);
    }

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

    private <T> INetCallback<T> wrapNetCallback(INetCallback<T> netCallback) {
        if (Preconditions.isNull(netCallback)) {
            if (!Preconditions.isNull(mCompleteCountDown))
                mCompleteCountDown.countDown();
            return null;
        }
        INetCallback<T> callback = new NetCallbackAdapter(netCallback) {
            @Override
            public void onNetStart() {
                if (Preconditions.isNull(mAlreadyStart) || mAlreadyStart.compareAndSet(false, true))
                    super.onNetStart();
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
    public <T> INetCancel post(String url, Map<String, String> params, Map<String, String> headers, INetCallback<T> callback) {
        if (Preconditions.isNull(mLoadingResponseBodyObserver)) return null;
        Observable<ResponseBody> observable = null;
        IRetrofitCommonApi api = RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class);
        if (Preconditions.isEmpty(headers) && Preconditions.isEmpty(params)) {
            observable = api.post(url);
        } else if (Preconditions.isEmpty(params)) {
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
    public <T> INetCancel get(String url, Map<String, String> params, Map<String, String> headers, INetCallback<T> callback) {
        if (Preconditions.isNull(mLoadingResponseBodyObserver)) return null;
        Observable<ResponseBody> observable = null;
        IRetrofitCommonApi api = RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class);
        if (Preconditions.isEmpty(headers) && Preconditions.isEmpty(params)) {
            observable = api.get(url);
        } else if (Preconditions.isEmpty(params)) {
            observable = api.get(url, params);
        } else {
            observable = api.get(url, params, headers);
        }
        return new RetrofitNetCancel(mLoadingResponseBodyObserver.subscribe(observable, callback));
    }
}
