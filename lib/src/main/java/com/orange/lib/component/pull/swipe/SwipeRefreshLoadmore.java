package com.orange.lib.component.pull.swipe;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.mvp.view.ifc.Ipull;
import com.orange.lib.utils.base.Preconditions;

/**
 * SwipeRefreshLayout实现IRefreshLoadmore
 */
public class SwipeRefreshLoadmore implements Ipull {
    //    protected IFooter mFooter;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;

    /**
     * 构造方法
     *
     * @param refreshLayout
     * @param recyclerView
     */
    public SwipeRefreshLoadmore(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView) {
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
    }

    @Override
    public void enableRefresh(boolean enable) {
        if (!Preconditions.isNull(mRecyclerView))
            mRefreshLayout.setEnabled(enable);
    }

    @Override
    public void enableLoadmore(boolean enable) {

    }

    @Override
    public void autoRefresh() {
        if (!Preconditions.isNull(mRecyclerView))
            mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void finishRefresh() {
        if (!Preconditions.isNull(mRecyclerView))
            mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void finishLoadmore() {
        //nothing
    }
}
