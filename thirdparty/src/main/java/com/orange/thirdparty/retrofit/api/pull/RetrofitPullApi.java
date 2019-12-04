package com.orange.thirdparty.retrofit.api.pull;

import androidx.core.util.Preconditions;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.callback.pull.IPullNetCallback;
import com.orange.lib.mvp.model.net.api.pull.IPullApi;
import com.orange.thirdparty.retrofit.IRetrofitApi;
import com.orange.thirdparty.retrofit.RetrofitClient;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.rxjava.PullResponseBodyObserver;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class RetrofitPullApi implements IPullApi {
    private volatile static RetrofitPullApi sInstance;

    public static RetrofitPullApi getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitPullApi.class) {
                if (null == sInstance)
                    sInstance = new RetrofitPullApi();
            }
        }
        return sInstance;
    }

    private PullResponseBodyObserver mPullResponseBodyObserver;

    public <T> RetrofitPullApi() {
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
    public <T> INetCancel get(String url, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class).get(url, params), type, callback);
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
    public <T> INetCancel get(Map<String, String> headers, String url, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class).get(url, params, headers), type, callback);
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
    public <T> INetCancel post(String url, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class).post(url, params), type, callback);
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
    public <T> INetCancel post(Map<String, String> headers, String url, Map<String, String> params, Type type, IPullNetCallback<T> callback) {
        Preconditions.checkNotNull(mPullResponseBodyObserver);
        Disposable subsribe = mPullResponseBodyObserver.subsribe(RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class).post(url, params, headers), type, callback);
        return new RetrofitNetCancel(subsribe);
    }
}