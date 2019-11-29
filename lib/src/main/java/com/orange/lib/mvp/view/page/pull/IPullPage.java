package com.orange.lib.mvp.view.page.pull;

/**
 * 页面状态：loading、content、empty、error
 */
public interface IPullPage {
    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 显示content
     */
    void showContent();

    /**
     * 显示empty
     */
    void showEmpty();

    /**
     * 显示error
     */
    void showError();
}
