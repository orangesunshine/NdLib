package com.orange.lib.component.pull.callback;

public interface IPullCallback {
    /**
     * 下拉刷新
     */
    void onPullRefresh();

    /**
     * 上拉加载
     */
    void onPullLoadMore();

    /**
     * 获取当前页数
     *
     * @return
     */
    int getCurPage();
}
