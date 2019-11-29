package com.orange.lib.mvp.view.page.pull;

import android.view.View;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.view.page.pull.IPullPage;
import com.orange.lib.utils.view.Views;

public class PullPage implements IPullPage {
    private View mLoadView;//菊花
    private View mContentView;//content页面
    private View mEmptyView;//content页面
    private View mErrorView;//错误页面

    /**
     * 构造方法
     *
     * @param holder
     */
    public PullPage(IHolder holder) {
        this(holder.getView(R.id.id_loading_orange), holder.getView(R.id.id_content_orange), holder.getView(R.id.id_empty_orange), holder.getView(R.id.id_error_orange));
    }

    /**
     * 构造方法
     *
     * @param loadView    loading页面
     * @param contentView content页面
     * @param emptyView   空页面
     * @param errorView   错误页面
     */
    public PullPage(View loadView, View contentView, View emptyView, View errorView) {
        mLoadView = loadView;
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
        Views.setVisible(mLoadView, true);
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmptyView, false);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        Views.setVisible(mLoadView, false);
        Views.setVisible(mContentView, true);
        Views.setVisible(mEmptyView, false);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示empty
     */
    @Override
    public void showEmpty() {
        Views.setVisible(mLoadView, false);
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmptyView, true);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        Views.setVisible(mLoadView, false);
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmptyView, false);
        Views.setVisible(mErrorView, true);
    }
}
