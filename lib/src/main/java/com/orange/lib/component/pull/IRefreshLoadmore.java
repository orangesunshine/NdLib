package com.orange.lib.component.pull;

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
}
