package com.orange.lib.mvp.model.net.callback.loading;

import com.orange.lib.mvp.view.page.loading.IPage;

public class PageCallback<T> extends AbsCallback<T> {
    private IPage mPageStatus;

    public PageCallback(IPage pageStatus) {
        mPageStatus = pageStatus;
    }

    /**
     * 网络请求开始
     */
    @Override
    public void onStart() {
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
        super.onError(code, error);
        if (null != mPageStatus)
            mPageStatus.showError();
    }
}
