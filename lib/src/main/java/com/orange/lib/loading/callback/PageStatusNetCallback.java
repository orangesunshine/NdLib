package com.orange.lib.loading.callback;

import com.orange.lib.loading.pagestatus.IPageStatus;

public class PageStatusNetCallback<T> implements INetCallback<T> {
    private IPageStatus mPageStatus;

    public PageStatusNetCallback(IPageStatus pageStatus) {
        mPageStatus = pageStatus;
    }

    /**
     * 网络请求开始
     */
    @Override
    public void onNetStart() {
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
        if (null != mPageStatus)
            mPageStatus.showError();
    }

    /**
     * 网络请求完成
     */
    @Override
    public void onComplete() {

    }
}
