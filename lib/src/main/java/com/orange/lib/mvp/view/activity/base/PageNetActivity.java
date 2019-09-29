package com.orange.lib.mvp.view.activity.base;

import com.orange.lib.loading.pagestatus.IPageStatus;
import com.orange.lib.loading.pagestatus.LoadingDialogPageStatus;
import com.orange.lib.mvp.presenter.ifc.INetPresenter;
import com.orange.lib.mvp.view.activity.NetActivity;
import com.orange.lib.mvp.view.ifc.base.IPageNetView;
import com.orange.lib.utils.base.Preconditions;

public abstract class PageNetActivity<P extends INetPresenter> extends NetActivity<P> implements IPageNetView {
    protected IPageStatus mPageStatus;

    @Override
    protected void attachStub() {
        super.attachStub();
        mPageStatus = buildPageStatus();
        if (null == mPageStatus)
            mPageStatus = new LoadingDialogPageStatus(mLoading, mHolder);
    }

    protected IPageStatus buildPageStatus() {
        return null;
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        if(!Preconditions.isNull(mPageStatus))
            mPageStatus.showContent();
    }

    /**
     * 显示空数据
     */
    @Override
    public void showEmpty() {
        if(!Preconditions.isNull(mPageStatus))
            mPageStatus.showEmpty();
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        if(!Preconditions.isNull(mPageStatus))
            mPageStatus.showError();
    }
}
