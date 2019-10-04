package com.orange.thirdparty.retrofit.api;


import androidx.core.util.Preconditions;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.loading.callback.INetCallback;
import com.orange.lib.loading.api.IPrefixSuffixApi;
import com.orange.thirdparty.retrofit.IRetrofitCommonApi;
import com.orange.thirdparty.retrofit.RetrofitClient;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.rxjava.LoadingResponseBodyObserver;

import java.util.Map;

import io.reactivex.disposables.Disposable;

public class RetrofitPrefixSuffixApi implements IPrefixSuffixApi {
    private volatile static RetrofitPrefixSuffixApi sInstance;

    public static RetrofitPrefixSuffixApi getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitPrefixSuffixApi.class) {
                if (null == sInstance)
                    sInstance = new RetrofitPrefixSuffixApi();
            }
        }
        return sInstance;
    }

    private LoadingResponseBodyObserver mLoadingResponseBodyObserver;

    public <T> RetrofitPrefixSuffixApi() {
        mLoadingResponseBodyObserver = LoadingResponseBodyObserver.newInstance();
    }

    /**
     * loading方式get网络请求
     *
     * @param prefix   一级路径
     * @param suffix   二级路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel get(String prefix, String suffix, Map<String, String> params, INetCallback<T> callback) {
        Preconditions.checkNotNull(mLoadingResponseBodyObserver);
        Disposable subsribe = mLoadingResponseBodyObserver.subscribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class).get(prefix, suffix, params), callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * loading方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param prefix   一级路径
     * @param suffix   二级路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel get(Map<String, String> headers, String prefix, String suffix, Map<String, String> params, INetCallback<T> callback) {
        Preconditions.checkNotNull(mLoadingResponseBodyObserver);
        Disposable subsribe = mLoadingResponseBodyObserver.subscribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class).get(headers, prefix, suffix, params), callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * loading方式post网络请求
     *
     * @param prefix   一级路径
     * @param suffix   二级路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel post(String prefix, String suffix, Map<String, String> params, INetCallback<T> callback) {
        Preconditions.checkNotNull(mLoadingResponseBodyObserver);
        Disposable subsribe = mLoadingResponseBodyObserver.subscribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class).post(prefix, suffix, params), callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * loading方式post网络请求，带请求头
     *
     * @param headers  请求头
     * @param prefix   一级路径
     * @param suffix   二级路径
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T> INetCancel post(Map<String, String> headers, String prefix, String suffix, Map<String, String> params, INetCallback<T> callback) {
        Preconditions.checkNotNull(mLoadingResponseBodyObserver);
        Disposable subsribe = mLoadingResponseBodyObserver.subscribe(RetrofitClient.getRetrofitInstance().create(IRetrofitCommonApi.class).post(prefix, suffix, params, headers), callback);
        return new RetrofitNetCancel(subsribe);
    }
}
