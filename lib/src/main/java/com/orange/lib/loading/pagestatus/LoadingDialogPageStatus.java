package com.orange.lib.loading.pagestatus;

import android.view.View;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;
import com.orange.lib.utils.view.Views;

/**
 * loadingdialog 实现
 */
public class LoadingDialogPageStatus implements IPageStatus {
    private ILoadingDialog mLoadingDialog;//菊花
    private View mContentView;//content页面
    private View mErrorView;//错误页面
    private View mEmtpyView;//空数据页面

    /**
     * holder构造方法
     *
     * @param loadingDialog
     * @param holder
     */
    public LoadingDialogPageStatus(ILoadingDialog loadingDialog, IHolder holder) {
        this(loadingDialog, holder.getView(R.id.id_content_orange), holder.getView(R.id.id_empty_orange), holder.getView(R.id.id_error_orange));
    }

    /**
     * 构造方法
     *
     * @param loadingDialog
     * @param contentView
     * @param errorView
     */
    public LoadingDialogPageStatus(ILoadingDialog loadingDialog, View contentView, View emptyView, View errorView) {
        mLoadingDialog = loadingDialog;
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
        if (null != mLoadingDialog)
            mLoadingDialog.showLoadingDialog();
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        if (null != mLoadingDialog)
            mLoadingDialog.dismissLoadingDialog();
        Views.setVisible(mContentView, true);
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showEmpty() {
        if (null != mLoadingDialog)
            mLoadingDialog.dismissLoadingDialog();
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmtpyView, true);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        if (null != mLoadingDialog)
            mLoadingDialog.dismissLoadingDialog();
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, true);
    }
}
