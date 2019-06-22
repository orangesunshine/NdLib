package com.orange.lib.component.loading;

/**
 * 页面状态：loading、content、empty、neterror
 */
public interface ILoadingLayout {
    void showLoading();

    void showContent();

    void showEmpty();

    void showNetError();
}
