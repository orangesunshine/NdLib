package com.orange.lib.mvp.view.pull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.constance.ITag;
import com.orange.lib.utils.Preconditions;
import com.orange.lib.utils.recyclerview.RecyclerViews;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 10:53
 */
public class SwipeRecyclerViewPull implements IPull {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    public SwipeRecyclerViewPull(IHolder commonHolder) {
        this(commonHolder.getView(R.id.id_refreshlayout_orange), commonHolder.getView(R.id.id_recyclerview_orange));
    }

    public SwipeRecyclerViewPull(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView) {
        mSwipeRefreshLayout = swipeRefreshLayout;
        mRecyclerView = recyclerView;
        if (null != mRecyclerView && RecyclerViews.isBottom(mRecyclerView)) {
        }
    }

    @Override
    public void enableRefresh(boolean enable) {
        if (Preconditions.isNull(mSwipeRefreshLayout)) return;
        mSwipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void enableLoadmore(boolean enable) {
        if (Preconditions.isNull(mRecyclerView)) return;
        mRecyclerView.setTag(ITag.TAG_LOADMORE_RECYCLERVIEW, enable);
    }

    @Override
    public void autoRefresh() {
        if (Preconditions.isNull(mSwipeRefreshLayout)) return;
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    @Override
    public boolean isLoadmore() {
        return false;
    }

    @Override
    public void finishRefresh() {
        if (Preconditions.isNull(mSwipeRefreshLayout)) return;
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void finishLoadmore() {

    }

    class OnScrollerMoreListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && RecyclerViews.isBottom(recyclerView)) {

            }
        }
    }
}
