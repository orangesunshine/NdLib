package com.orange.thirdparty.retrofit.api.pull;

import androidx.core.util.Preconditions;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.pull.callback.IPullNetCallback;
import com.orange.lib.pull.api.IPullUrlApi;
import com.orange.thirdparty.retrofit.IRetrofitCommonApi;
import com.orange.thirdparty.retrofit.RetrofitClient;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.rxjava.PullResponseBodyObserver;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class RetrofitPullUrlApi implements IPullUrlApi {
    private volatile static RetrofitPullUrlApi sInstance;

    public static RetrofitPullUrlApi getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitPullUrlApi.class) {
                if (null == sInstance)
                    sInstance = new RetrofitPullUrlApi();
            }
        }
        return sInstance;
    }

    private PullResponseBodyObserver mPullResponseBodyObserver;

    public <T> RetrofitPullUrlApi() {
        mPullResponseBodyObserver = PullResponseBodyObserver.newInstance();
    }

    /**
     * pull方式post网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel getPull(String url, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class).get(url, params), type, callback);
        return new RetrofitNetCancel(subsribe);
    }


    /**
     * pull方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel getPull(Map<String, String> headers, String url, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class).get(url, params, headers), type, callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * pull方式post网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel postPull(String url, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class).post(url, params), type, callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * pull方式post网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel postPull(Map<String, String> headers, String url, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class).post(url, params, headers), type, callback);
        return new RetrofitNetCancel(subsribe);
    }
}
