package com.orange.thirdparty.retrofit.api;

import androidx.core.util.Preconditions;

import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.loading.callback.INetCallback;
import com.orange.lib.loading.api.IUrlApi;
import com.orange.thirdparty.retrofit.IRetrofitCommonApiCallback;
import com.orange.thirdparty.retrofit.RetrofitClient;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.retrofit.api.pull.RetrofitPullUrlApi;
import com.orange.thirdparty.rxjava.LoadingResponseBodyObserver;

import java.util.Map;

import io.reactivex.disposables.Disposable;

public class RetrofitUrlApi implements IUrlApi {
    private volatile static RetrofitUrlApi sInstance;

    public static <T> RetrofitUrlApi getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitPullUrlApi.class) {
                if (null == sInstance)
                    sInstance = new RetrofitUrlApi();
            }
        }

        return sInstance;
    }

    private LoadingResponseBodyObserver mLoadingResponseBodyObserver;

    public <T> RetrofitUrlApi() {
        mLoadingResponseBodyObserver = LoadingResponseBodyObserver.newInstance();
    }

    /**
     * loading方式get网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel get(String url, Map<String, String> params, INetCallback<T> callback) {
        Preconditions.checkNotNull(mLoadingResponseBodyObserver);
        Disposable subsribe = mLoadingResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApiCallback.class).get(url, params), callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * loading方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel get(Map<String, String> headers, String url, Map<String, String> params, INetCallback<T> callback) {
        Preconditions.checkNotNull(mLoadingResponseBodyObserver);
        Disposable subsribe = mLoadingResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApiCallback.class).get(headers, url, params), callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * loading方式post网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel post(String url, Map<String, String> params, INetCallback<T> callback) {
        Preconditions.checkNotNull(mLoadingResponseBodyObserver);
        Disposable subsribe = mLoadingResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApiCallback.class).post(url, params), callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * loading方式post网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel post(Map<String, String> headers, String url, Map<String, String> params, INetCallback<T> callback) {
        Preconditions.checkNotNull(mLoadingResponseBodyObserver);
        Disposable subsribe = mLoadingResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApiCallback.class).post(headers, url, params), callback);
        return new RetrofitNetCancel(subsribe);
    }
}
