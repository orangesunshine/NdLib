package com.orange.lib.component.pull.swipe;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.IPullCallback;
import com.orange.lib.utils.PageUtils;


public class SwipeRefreshLoadmore implements IRefreshLoadmore {
    private IFooter mFooter;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected IPullCallback mPullCallback;

    public SwipeRefreshLoadmore(SwipeRefreshLayout refreshLayout, IFooter footer, RecyclerView recyclerView) {
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        mFooter = footer;
        PageUtils.resetPageindexTag(refreshLayout);
    }

    public SwipeRefreshLoadmore(IHolder holder) {
        mRefreshLayout = holder.getView(R.id.refreshlayout);
        mRecyclerView = holder.getView(R.id.recyclerview);
        mFooter = new DefaultFooter(holder);
        PageUtils.resetPageindexTag(mRefreshLayout);
    }

    public void setPullCallback(IPullCallback callback) {
        mPullCallback = callback;
        if (null != mRefreshLayout) {
            mRefreshLayout.setOnRefreshListener(() -> {
                if (null != callback)
                    callback.onPullRefresh();
            });
        }
        if (null != mRecyclerView)
            mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
                @Override
                public void onLoadMore() {
                    if (null != callback)
                        callback.onPullLoadMore();
                }
            });
    }

    @Override
    public void refresh() {
        if (null != mRefreshLayout)
            mRefreshLayout.setRefreshing(true);
    }

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
        mFooter.disableLoadmore();
    }

    /**
     * 获取当前页数
     *
     * @return
     */
    @Override
    public int getCurPage() {
        return PageUtils.getPageindex(mRefreshLayout);
    }
}
