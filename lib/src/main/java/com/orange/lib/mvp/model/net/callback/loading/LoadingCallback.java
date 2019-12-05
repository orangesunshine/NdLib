package com.orange.lib.mvp.model.net.callback.loading;


import com.orange.lib.mvp.view.loading.ILoading;

public class LoadingCallback<T> extends AbsCallback<T> {
    private ILoading loading;
    private LogCallback mLogNetCallback = new LogCallback();

    public LoadingCallback(ILoading loading) {
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
        super.onComplete();
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
