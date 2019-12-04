package com.orange.lib.mvp.model.net.callback.loading;

import com.orange.lib.mvp.view.page.loading.IPage;

public class PageCallback<T> implements ICallback<T> {
    private IPage mPageStatus;
    private LogCallback mLogNetCallback = new LogCallback();

    public PageCallback(IPage pageStatus) {
        mPageStatus = pageStatus;
    }

    /**
     * 网络请求开始
     */
    @Override
    public void onStart() {
        if (null != mLogNetCallback)
            mLogNetCallback.onStart();
        if (null != mPageStatus)
            mPageStatus.showLoading();
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
        if (null != mPageStatus)
            mPageStatus.showContent();
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
        if (null != mPageStatus)
            mPageStatus.showError();
    }

    /**
     * 网络请求完成
     */
    @Override
    public void onComplete() {
        if (null != mLogNetCallback)
            mLogNetCallback.onComplete();
    }
}
