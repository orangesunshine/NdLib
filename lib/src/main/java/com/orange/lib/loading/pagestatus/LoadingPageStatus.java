package com.orange.lib.loading.pagestatus;

import android.view.View;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.utils.ViewUtils;

public class LoadingPageStatus implements IPageStatus {
    private View mLoadView;//菊花
    private View mContentView;//content页面
    private View mErrorView;//错误页面

    /**
     * 构造方法
     *
     * @param holder
     */
    public LoadingPageStatus(IHolder holder) {
        this(holder.getView(R.id.loading_id), holder.getView(R.id.content_id), holder.getView(R.id.error_id));
    }

    /**
     * 构造方法
     *
     * @param loadView    loading页面
     * @param contentView content页面
     * @param errorView   错误页面
     */
    public LoadingPageStatus(View loadView, View contentView, View errorView) {
        mLoadView = loadView;
        mContentView = contentView;
        mErrorView = errorView;
        showContent();
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        ViewUtils.setVisible(mLoadView, true);
        ViewUtils.setVisible(mContentView, false);
        ViewUtils.setVisible(mErrorView, false);
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        ViewUtils.setVisible(mLoadView, false);
        ViewUtils.setVisible(mContentView, true);
        ViewUtils.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        ViewUtils.setVisible(mLoadView, false);
        ViewUtils.setVisible(mContentView, false);
        ViewUtils.setVisible(mErrorView, true);
    }
}
