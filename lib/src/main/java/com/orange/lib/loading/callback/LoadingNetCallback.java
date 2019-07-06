package com.orange.lib.loading.callback;


import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;

public class LoadingNetCallback<T> implements INetCallback<T> {
    private ILoadingDialog loading;
    private LogLoadingNetCallback mLogLoadingNetCallback = new LogLoadingNetCallback();

    public LoadingNetCallback(ILoadingDialog loading) {
        this.loading = loading;
    }

    @Override
    public void onNetStart() {
        if (null != mLogLoadingNetCallback)
            mLogLoadingNetCallback.onNetStart();
        if (null != loading)
            loading.showLoadingDialog();
    }

    /**
     * 成功
     *
     * @param t
     */
    @Override
    public void onSuccess(T t) {
        if (null != mLogLoadingNetCallback)
            mLogLoadingNetCallback.onSuccess(t);
    }

    @Override
    public void onComplete() {
        if (null != mLogLoadingNetCallback)
            mLogLoadingNetCallback.onComplete();
        if (null != loading)
            loading.dismissLoadingDialog();
    }

    /**
     * 失败
     *
     * @param code
     * @param error
     */
    @Override
    public void onError(int code, Throwable error) {
        if (null != mLogLoadingNetCallback)
            mLogLoadingNetCallback.onError(code, error);
    }
}
