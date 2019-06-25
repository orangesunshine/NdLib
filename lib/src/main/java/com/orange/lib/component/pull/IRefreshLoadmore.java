package com.orange.lib.component.pull;

import android.view.View;

import com.orange.lib.component.pull.callback.IPullCallback;

public interface IRefreshLoadmore {

    /**
     * 刷新
     */
    void refresh();

    /**
     * 加载
     */
    void loadmore();

    /**
     * 设置能否加载
     *
     * @param enable
     * @return
     */
    void enableLoadMore(boolean enable);

    /**
     * 获取当前页数
     *
     * @return
     */
    int getCurPage();

    /**
     * 设置回调
     *
     * @param callback
     */
    void setPullCallback(IPullCallback callback);
}
