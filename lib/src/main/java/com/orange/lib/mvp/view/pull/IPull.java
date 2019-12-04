package com.orange.lib.mvp.view.pull;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:44
 */
public interface IPull {
    /**
     * 设置能否刷新
     *
     * @param enable
     */
    void enableRefresh(boolean enable);

    /**
     * 设置能否加载
     *
     * @param enable
     */
    void enableLoadmore(boolean enable);

    /**
     * 自动刷新
     */
    void autoRefresh();

    /**
     * 是否正在刷新
     *
     * @return
     */
    boolean isRefreshing();

    /**
     * 是否正在加载更多
     *
     * @return
     */
    boolean isLoadmore();

    /**
     * 关闭刷新
     */
    void finishRefresh();

    /**
     * 关闭加载更多
     */
    void finishLoadmore();
}
