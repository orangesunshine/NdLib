package com.orange.thirdparty.smartfreshlayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.IPullCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * SmartRefreshLayout实现IRefreshLoadmore
 */
public class SmartRefreshLoadmore implements IRefreshLoadmore {
    protected SmartRefreshLayout mRefreshLayout;//刷新控件
    protected RecyclerView mRecyclerView;
    protected IPullCallback mPullCallback;//刷新、加载回调

    /**
     * 构造方法
     *
     * @param refreshLayout
     * @param recyclerView
     */
    public SmartRefreshLoadmore(SmartRefreshLayout refreshLayout, RecyclerView recyclerView) {
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
    public SmartRefreshLoadmore(SmartRefreshLayout refreshLayout, RecyclerView recyclerView, IPullCallback pullCallback) {
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        setPullCallback(pullCallback);
    }

    /**
     * 设置刷新、加载回调
     *
     * @param callback
     */
    public void setPullCallback(IPullCallback callback) {
        mPullCallback = callback;
        if (null != mRefreshLayout)
            mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    if (null != callback)
                        callback.onPullLoadMore();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    if (null != callback)
                        callback.onPullRefresh();
                }
            });
    }

    /**
     * 刷新
     */
    @Override
    public void refresh() {
        if (null != mRefreshLayout) {
            mRefreshLayout.resetNoMoreData();
            mRefreshLayout.autoRefresh();
        }
    }

    /**
     * 加载
     */
    @Override
    public void loadmore() {
        if (null != mRefreshLayout)
            mRefreshLayout.autoLoadMore();
    }

    /**
     * 设置能否加载
     *
     * @param enable
     * @return
     */
    @Override
    public void enableLoadMore(boolean enable) {
        if (null != mRefreshLayout)
            mRefreshLayout.setEnableLoadMore(enable);
    }
}
