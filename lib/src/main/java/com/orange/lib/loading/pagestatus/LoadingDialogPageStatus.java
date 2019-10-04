package com.orange.lib.loading.pagestatus;

import android.view.View;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.utils.view.Views;

/**
 * loadingdialog 实现
 */
public class LoadingDialogPageStatus implements IPageStatus {
    private ILoading mLoading;//菊花
    private View mContentView;//content页面
    private View mErrorView;//错误页面
    private View mEmtpyView;//空数据页面

    /**
     * holder构造方法
     *
     * @param loading
     * @param holder
     */
    public LoadingDialogPageStatus(ILoading loading, IHolder holder) {
        this(loading, holder.getView(R.id.id_content_orange), holder.getView(R.id.id_empty_orange), holder.getView(R.id.id_error_orange));
    }

    /**
     * 构造方法
     *
     * @param loading
     * @param contentView
     * @param errorView
     */
    public LoadingDialogPageStatus(ILoading loading, View contentView, View emptyView, View errorView) {
        mLoading = loading;
        mContentView = contentView;
        mErrorView = errorView;
        mEmtpyView = emptyView;
        showContent();
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (null != mLoading)
            mLoading.showLoading();
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 关闭loading
     */
    @Override
    public void hideLoading() {
        if (null != mLoading)
            mLoading.hideLoading();
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        hideLoading();
        Views.setVisible(mContentView, true);
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showEmpty() {
        hideLoading();
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmtpyView, true);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        hideLoading();
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, true);
    }
}
