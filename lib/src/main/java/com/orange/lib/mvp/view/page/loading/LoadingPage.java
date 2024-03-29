package com.orange.lib.mvp.view.page.loading;

import android.view.View;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.utils.view.Views;

public class LoadingPage implements IPage {
    private View mLoadView;//菊花
    private View mContentView;//content页面
    private View mErrorView;//错误页面
    private View mEmtpyView;//空数据页面

    /**
     * 构造方法
     *
     * @param holder
     */
    public LoadingPage(IHolder holder) {
        this(holder.getView(R.id.id_loading_orange), holder.getView(R.id.id_content_orange), holder.getView(R.id.id_empty_orange), holder.getView(R.id.id_error_orange));
    }

    /**
     * 构造方法
     *
     * @param loadView    loading页面
     * @param contentView content页面
     * @param errorView   错误页面
     */
    public LoadingPage(View loadView, View contentView, View emptyView, View errorView) {
        mLoadView = loadView;
        mContentView = contentView;
        mEmtpyView = emptyView;
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
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 关闭loading
     */
    @Override
    public void dismissLoading() {
        Views.setVisible(mLoadView, false);
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        Views.setVisible(mLoadView, false);
        Views.setVisible(mContentView, true);
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showEmpty() {
        Views.setVisible(mLoadView, false);
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmtpyView, true);
        Views.setVisible(mErrorView, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        Views.setVisible(mLoadView, false);
        Views.setVisible(mContentView, false);
        Views.setVisible(mEmtpyView, false);
        Views.setVisible(mErrorView, true);
    }
}
