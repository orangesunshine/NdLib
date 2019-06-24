package com.orange.lib.component.pull;

public class RefreshLoadmoreAdapter implements IRefreshLoadmore {
    protected IRefreshLoadmore mPull;

    /**
     * 刷新
     */
    @Override
    public void refresh() {
        if (null != mPull)
            mPull.refresh();
    }

    /**
     * 加载
     */
    @Override
    public void loadmore() {
        if (null != mPull)
            mPull.loadmore();
    }

    /**
     * 设置能否加载
     *
     * @param enable
     * @return
     */
    @Override
    public void enableLoadMore(boolean enable) {
        if (null != mPull)
            mPull.enableLoadMore(enable);
    }
}
