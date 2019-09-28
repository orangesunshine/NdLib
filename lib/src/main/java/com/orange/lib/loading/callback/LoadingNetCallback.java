package com.orange.lib.loading.callback;


import com.orange.lib.mvp.view.ifc.ILoading;

public class LoadingNetCallback<T> implements INetCallback<T> {
    private ILoading loading;
    private LogNetCallback mLogNetCallback = new LogNetCallback();

    public LoadingNetCallback(ILoading loading) {
        this.loading = loading;
    }

    @Override
    public void onNetStart() {
        if (null != mLogNetCallback)
            mLogNetCallback.onNetStart();
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
            loading.hideLoading();
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
