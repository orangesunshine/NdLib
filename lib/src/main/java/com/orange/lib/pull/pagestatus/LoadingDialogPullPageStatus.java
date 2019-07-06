package com.orange.lib.pull.pagestatus;

import android.view.View;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;
import com.orange.lib.loading.pagestatus.IPageStatus;
import com.orange.lib.pull.pagestatus.IPullPageStatus;
import com.orange.lib.utils.ViewUtils;

/**
 * loadingdialog 实现
 */
public class LoadingDialogPullPageStatus implements IPullPageStatus {
    private ILoadingDialog mLoadingDialog;//菊花
    private View mContentView;//content页面
    private View mEmptyView;//空页面
    private View mErrorView;//错误页面

    /**
     * holder构造方法
     *
     * @param loadingDialog
     * @param holder
     */
    public LoadingDialogPullPageStatus(ILoadingDialog loadingDialog, IHolder holder) {
        this(loadingDialog, holder.getView(R.id.content_id), holder.getView(R.id.empty_id), holder.getView(R.id.error_id));
    }

    /**
     * 构造方法
     *
     * @param loadingDialog
     * @param contentView
     * @param emptyView
     * @param errorView
     */
    public LoadingDialogPullPageStatus(ILoadingDialog loadingDialog, View contentView, View emptyView, View errorView) {
        mLoadingDialog = loadingDialog;
        mContentView = contentView;
        mEmptyView = emptyView;
        mErrorView = errorView;
        showContent();
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (null != mLoadingDialog)
            mLoadingDialog.showLoadingDialog();
        ViewUtils.setVisible(mContentView, false);
        ViewUtils.setVisible(mEmptyView, false);
        ViewUtils.setVisible(mErrorView, false);
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        if (null != mLoadingDialog)
            mLoadingDialog.dismissLoadingDialog();
        ViewUtils.setVisible(mContentView, true);
        ViewUtils.setVisible(mEmptyView, false);
        ViewUtils.setVisible(mErrorView, false);
    }

    /**
     * 显示empty
     */
    @Override
    public void showEmpty() {
        if (null != mLoadingDialog)
            mLoadingDialog.dismissLoadingDialog();
        ViewUtils.setVisible(mContentView, false);
        ViewUtils.setVisible(mEmptyView, true);
        ViewUtils.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        if (null != mLoadingDialog)
            mLoadingDialog.dismissLoadingDialog();
        ViewUtils.setVisible(mContentView, false);
        ViewUtils.setVisible(mEmptyView, false);
        ViewUtils.setVisible(mErrorView, true);
    }
}
