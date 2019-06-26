package com.orange.lib.mvp.model.net.callback;

import com.orange.lib.component.pagestatus.IPageStatus;

public class PageStatusNetCallback<T> extends DefaultNetCallback<T> {
    private IPageStatus mPageStatus;

    public PageStatusNetCallback(IPageStatus pageStatus) {
        mPageStatus = pageStatus;
    }

    /**
     * 网络请求开始
     */
    @Override
    public void onNetStart() {
        super.onNetStart();
        if (null != mPageStatus)
            mPageStatus.showLoading();
    }

    /**
     * @param successs true:onSuccess,false:onError
     * @param noData   没有更多数据：用于pull
     * @param empty    结果数据为空
     */
    @Override
    public void onComplete(boolean successs, boolean noData, boolean empty) {
        super.onComplete(successs, noData, empty);
        if (null != mPageStatus) {
            if (!successs) {
                mPageStatus.showError();
            } else if (empty) {
                mPageStatus.showEmpty();
            } else {
                mPageStatus.showContent();
            }
        }
    }
}
