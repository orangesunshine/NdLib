package com.orange.lib.pull.adapterpattern;

import com.orange.lib.pull.callback.IPullNetCallback;

public class PullNetCallbackAdapter<T> implements IPullNetCallback<T> {
    private IPullNetCallback<T> mNetCallback;

    public PullNetCallbackAdapter(IPullNetCallback<T> netCallback) {
        mNetCallback = netCallback;
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
    public void onComplete(boolean successs, boolean noData, boolean empty) {
        if (null != mNetCallback)
            mNetCallback.onComplete(successs, noData, empty);
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
