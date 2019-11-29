package com.orange.lib.mvp.contact.view;

import com.orange.lib.R;
import com.orange.lib.activity.retry.RetryListener;
import com.orange.lib.mvp.view.page.loading.IPage;
import com.orange.lib.mvp.view.page.loading.LoadingDialogPage;
import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.utils.base.Preconditions;
import com.orange.lib.utils.view.Views;

public abstract class NetActivity<P extends INetContact.Presenter> extends PresenterActivity<P> implements IPage {
    protected IPage mPage;//空、错误、正常页面

    @Override
    protected void init() {
        super.init();

        //网络错误点击刷新
        mHolder.addOnItemChildClick(new RetryListener(getClass()), R.id.id_retry_orange);
        mPage = getPage();
        if (null == mPage)
            mPage = new LoadingDialogPage(mLoading, mHolder);
    }

    /**
     * 根据占位类型插入占位视图（actbar，pull，content）
     */
    @Override
    protected void attachStub() {
        super.attachStub();//actbar

        //loading
        Views.attachStub(mHolder.getView(R.id.id_stub_loading_orange), R.layout.stub_layout_loading);

        //empty
        Views.attachStub(mHolder.getView(R.id.id_stub_empty_orange), R.layout.stub_layout_empty);

        //error
        Views.attachStub(mHolder.getView(R.id.id_stub_error_orange), R.layout.stub_layout_error);
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        if (!Preconditions.isNull(mPage))
            mPage.showContent();
    }

    /**
     * 显示空数据
     */
    @Override
    public void showEmpty() {
        if (!Preconditions.isNull(mPage))
            mPage.showEmpty();
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        if (!Preconditions.isNull(mPage))
            mPage.showError();
    }

    /**
     * 自定义空、错误、正常页面实现
     *
     * @return
     */
    protected IPage getPage() {
        return null;
    }
}
