package com.orange.lib.loading.pagestatus;

import com.orange.lib.mvp.view.ifc.ILoading;

/**
 * 页面状态：loading、content、empty、error
 */
public interface IPage extends ILoading {
    /**
     * 显示content
     */
    void showContent();

    /**
     * 显示空数据
     */
    void showEmpty();

    /**
     * 显示error
     */
    void showError();
}
