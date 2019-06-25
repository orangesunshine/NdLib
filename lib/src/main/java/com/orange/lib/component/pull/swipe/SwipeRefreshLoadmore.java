package com.orange.lib.component.pull.swipe;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.IPullCallback;

/**
 * SwipeRefreshLayout实现IRefreshLoadmore
 */
public class SwipeRefreshLoadmore implements IRefreshLoadmore {
    protected IFooter mFooter;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected IPullCallback mPullCallback;

    /**
     * 构造方法
     *
     * @param refreshLayout
     * @param footer
     * @param recyclerView
     */
    public SwipeRefreshLoadmore(SwipeRefreshLayout refreshLayout, IFooter footer, RecyclerView recyclerView) {
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        mFooter = footer;
    }

    /**
     * 构造方法
     *
     * @param refreshLayout
     * @param footer
     * @param recyclerView
     * @param pullCallback      刷新、加载回调
     */
    public SwipeRefreshLoadmore(SwipeRefreshLayout refreshLayout, IFooter footer, RecyclerView recyclerView, IPullCallback pullCallback) {
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        mFooter = footer;
        setPullCallback(pullCallback);
    }

    /**
     * 设置刷新、加载回调
     *
     * @param callback
     */
    @Override
    public void setPullCallback(IPullCallback callback) {
        mPullCallback = callback;
        if (null != mRefreshLayout)
            mRefreshLayout.setOnRefreshListener(() -> {
                if (null != callback)
                    callback.onPullRefresh();
            });
        if (null != mRecyclerView)
            mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
                @Override
                public void onLoadMore() {
                    if (null != callback)
                        callback.onPullLoadMore();
                }
            });
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
        if (null != mFooter)
            mFooter.showLoading();
    }

    /**
     * 设置能否加载
     *
     * @param enable
     * @return
     */
    @Override
    public void enableLoadMore(boolean enable) {
        if (null != mFooter)
            mFooter.disableLoadmore();
    }
}
