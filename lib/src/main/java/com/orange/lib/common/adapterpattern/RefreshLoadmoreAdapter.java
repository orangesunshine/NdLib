package com.orange.lib.common.adapterpattern;

import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.IPullCallback;

public class RefreshLoadmoreAdapter implements IRefreshLoadmore {
    protected IRefreshLoadmore mRefreshLoadmore;

    /**
     * 刷新
     */
    @Override
    public void refresh() {
        if (null != mRefreshLoadmore)
            mRefreshLoadmore.refresh();
    }

    /**
     * 加载
     */
    @Override
    public void loadmore() {
        if (null != mRefreshLoadmore)
            mRefreshLoadmore.loadmore();
    }

    /**
     * 设置能否加载
     *
     * @param enable
     * @return
     */
    @Override
    public void enableLoadMore(boolean enable) {
        if (null != mRefreshLoadmore)
            mRefreshLoadmore.enableLoadMore(enable);
    }

    /**
     * 设置刷新、加载回调
     *
     * @param callback
     */
    @Override
    public void setPullCallback(IPullCallback callback) {
        if (null != mRefreshLoadmore)
            mRefreshLoadmore.setPullCallback(callback);
    }
}
