package com.orange.lib.component.pull;

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
     * 设置刷新、加载回调
     *
     * @param callback
     */
    void setPullCallback(IPullCallback callback);
}