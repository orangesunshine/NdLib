package com.orange.lib.component.pagestatus;

/**
 * 页面状态：loading、content、empty、error
 */
public interface IPageStatus {
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
