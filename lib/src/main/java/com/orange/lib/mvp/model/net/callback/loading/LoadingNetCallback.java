package com.orange.lib.mvp.model.net.callback.loading;


import com.orange.lib.mvp.view.loading.ILoading;

public class LoadingNetCallback<T> implements INetCallback<T> {
    private ILoading loading;
    private LogNetCallback mLogNetCallback = new LogNetCallback();

    public LoadingNetCallback(ILoading loading) {
        this.loading = loading;
    }

    @Override
    public void onStart() {
        if (null != mLogNetCallback)
            mLogNetCallback.onStart();
        if (null != loading)
            loading.showLoading();
    }

    /**
     * 成功
     *
     * @param t
     */
    @Override
    public void onSuccess(T t) {
        if (null != mLogNetCallback)
            mLogNetCallback.onSuccess(t);
    }

    @Override
    public void onComplete() {
        if (null != mLogNetCallback)
            mLogNetCallback.onComplete();
        if (null != loading)
            loading.dismissLoading();
    }

    /**
     * 失败
     *
     * @param code
     * @param error
     */
    @Override
    public void onError(int code, Throwable error) {
        if (null != mLogNetCallback)
            mLogNetCallback.onError(code, error);
    }
}
