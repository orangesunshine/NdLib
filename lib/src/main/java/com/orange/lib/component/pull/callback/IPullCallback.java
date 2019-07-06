package com.orange.lib.component.pull.callback;

import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;

public interface IPullCallback {
    /**
     * 下拉刷新
     */
    INetCancel onPullRefresh();

    /**
     * 上拉加载
     */
    INetCancel onPullLoadMore();

    /**
     * 获取当前页数
     *
     * @return
     */
    int getCurPage();
}
