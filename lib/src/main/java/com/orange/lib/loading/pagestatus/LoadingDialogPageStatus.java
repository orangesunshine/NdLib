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

    /**
     * holder构造方法
     *
     * @param loadingDialog
     * @param holder
     */
    public LoadingDialogPageStatus(ILoadingDialog loadingDialog, IHolder holder) {
        this(loadingDialog, holder.getView(R.id.content_id), holder.getView(R.id.error_id));
    }

    /**
     * 构造方法
     *
     * @param loadingDialog
     * @param contentView
     * @param errorView
     */
    public LoadingDialogPageStatus(ILoadingDialog loadingDialog, View contentView, View errorView) {
        mLoadingDialog = loadingDialog;
        mContentView = contentView;
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
        Views.setVisible(mContentView, false);
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
        Views.setVisible(mErrorView, true);
    }
}
