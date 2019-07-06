package com.orange.lib.component.pull.swipe;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.IPullCallback;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;

import java.util.concurrent.atomic.AtomicReference;

/**
 * SwipeRefreshLayout实现IRefreshLoadmore
 */
public class SwipeRefreshLoadmore implements IRefreshLoadmore {
    //    protected IFooter mFooter;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected IPullCallback mPullCallback;

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

    /**
     * 构造方法
     *
     * @param refreshLayout
     * @param recyclerView
     * @param pullCallback  刷新、加载回调
     */
    public SwipeRefreshLoadmore(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, IPullCallback pullCallback) {
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        setPullCallback(pullCallback);
    }

    /**
     * 设置刷新、加载回调
     *
     * @param callback
     */
    @Override
    public INetCancel setPullCallback(IPullCallback callback) {
        mPullCallback = callback;
        AtomicReference<INetCancel> netCancel = new AtomicReference<>();
        if (null != mRefreshLayout)
            mRefreshLayout.setOnRefreshListener(() -> {
                if (null != callback)
                    netCancel.set(callback.onPullRefresh());
            });
        if (null != mRecyclerView)
            mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener(mRefreshLayout) {
                @Override
                public void onLoadMore() {
                    if (null != callback)
                        netCancel.set(callback.onPullLoadMore());
                }
            });
        return netCancel.get();
    }

    /**
     * 刷新
     */
    @Override
    public void refresh() {
        if (null != mRefreshLayout)
            mRefreshLayout.setRefreshing(true);
    }

    /**
     * 加载
     */
    @Override
    public void loadmore() {
    }

    /**
     * 设置能否加载
     *
     * @param enable
     * @return
     */
    @Override
    public void enableLoadMore(boolean enable) {
    }
}
