package com.orange.lib.loading.adapterpattern;

import com.orange.lib.loading.callback.INetCallback;

public class NetCallbackAdapter<T> implements INetCallback<T> {
    private INetCallback<T> mNetCallback;

    public NetCallbackAdapter(INetCallback<T> netCallback) {
        mNetCallback = netCallback;
    }

    /**
     * 网络请求开始
     */
    @Override
    public void onNetStart() {
        if (null != mNetCallback)
            mNetCallback.onNetStart();
    }

    /**
     * 成功
     *
     * @param t
     */
    @Override
    public void onSuccess(T t) {
        if (null != mNetCallback)
            mNetCallback.onSuccess(t);
    }

    /**
     * 完成
     */
    @Override
    public void onComplete() {
        if (null != mNetCallback)
            mNetCallback.onComplete();
    }

    /**
     * 失败
     *
     * @param code
     * @param error
     */
    @Override
    public void onError(int code, Throwable error) {
        if (null != mNetCallback)
            mNetCallback.onError(code, error);
    }
}
