package com.orange.thirdparty.retrofit.api.pull;


import androidx.core.util.Preconditions;

import com.orange.lib.common.reponse.PullData;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.callback.pull.IPullNetCallback;
import com.orange.lib.mvp.model.net.api.pull.IPullPrefixSuffixApi;
import com.orange.thirdparty.retrofit.api.IRetrofitApi;
import com.orange.thirdparty.retrofit.RetrofitClient;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.rxjava.PullResponseBodyObserver;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class RetrofitPullPrefixSuffixApi implements IPullPrefixSuffixApi {
    private volatile static RetrofitPullPrefixSuffixApi sInstance;

    public static RetrofitPullPrefixSuffixApi getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitPullPrefixSuffixApi.class) {
                if (null == sInstance)
                    sInstance = new RetrofitPullPrefixSuffixApi();
            }
        }
        return sInstance;
    }

    private PullResponseBodyObserver mPullResponseBodyObserver;

    public <T> RetrofitPullPrefixSuffixApi() {
        mPullResponseBodyObserver = PullResponseBodyObserver.newInstance();
    }

    /**
     * pull方式get网络请求
     *
     * @param prefix
     * @param suffix
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T extends PullData> INetCancel getPull(String prefix, String suffix, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class).get(prefix, suffix, params), type, callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * pull方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param prefix
     * @param suffix
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T extends PullData> INetCancel getPull(Map<String, String> headers, String prefix, String suffix, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class).get(headers, prefix, suffix, params), type, callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * pull方式post网络请求
     *
     * @param prefix
     * @param suffix
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T extends PullData> INetCancel postPull(String prefix, String suffix, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class).post(prefix, suffix, params), type, callback);
        return new RetrofitNetCancel(subsribe);
    }

    /**
     * pull方式post网络请求，带请求头
     *
     * @param headers  请求头
     * @param prefix
     * @param suffix
     * @param params   参数
     * @param callback 回调
     */
    @Override
    public <T extends PullData> INetCancel postPull(Map<String, String> headers, String prefix, String suffix, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class).post(prefix, suffix, params, headers), type, callback);
        return new RetrofitNetCancel(subsribe);
    }
}
