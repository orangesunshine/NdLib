package com.orange.lib.component.pull.swipe;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.AbstractPull;
import com.orange.lib.mvp.model.net.pull.IPageNetRequest;


public class SwipeRefreshLoadmore extends AbstractPull<SwipeRefreshLayout> implements IRefreshLoadmore {
    private IFooter mFooter;

    public <T> SwipeRefreshLoadmore(IHolder holder, IPageNetRequest<T> pageRequest) {
        super(holder, pageRequest);
        refreshLayout.setOnRefreshListener(() -> onPullRefresh());
        recyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                onPullLoadMore();
            }
        });
        mFooter = new DefaultFooter(holder);
    }

    @Override
    public void refresh() {
        if (null != refreshLayout)
            refreshLayout.setRefreshing(true);
    }

    @Override
    public void loadmore() {
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
}
