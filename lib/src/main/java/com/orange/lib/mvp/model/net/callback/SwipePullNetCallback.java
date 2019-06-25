package com.orange.lib.mvp.model.net.callback;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.common.reponse.PullData;
import com.orange.lib.component.pull.swipe.IFooter;
import com.orange.lib.component.recyclerview.CommonAdapter;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.utils.PageUtils;

public class SwipePullNetCallback<ITEM> extends DefaultNetCallback<PullData<ITEM>> {
    protected SwipeRefreshLayout mRefreshLayout;
    protected IFooter mFooter;
    protected RecyclerView mRecyclerView;
    protected View mEmptyView;
    protected int mItemLayoutId;
    protected IConvertRecyclerView<ITEM> mConvertRecyclerView;

    public SwipePullNetCallback(SwipeRefreshLayout refreshLayout, IFooter footer, RecyclerView recyclerView, View emptyView, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        this.mRefreshLayout = refreshLayout;
        mFooter = footer;
        mRecyclerView = recyclerView;
        mEmptyView = emptyView;
        mItemLayoutId = itemLayoutId;
        mConvertRecyclerView = convertRecyclerView;
    }

    @Override
    public void onSuccess(PullData<ITEM> pullResponse) {
        super.onSuccess(pullResponse);
        CommonAdapter.adapterDatas(mRefreshLayout.getContext(), mRecyclerView, mEmptyView, mItemLayoutId, null == pullResponse ? null : pullResponse.getList(), PageUtils.isLoadmore(mRefreshLayout), mConvertRecyclerView);
    }

    @Override
    public void onComplete(boolean successs, boolean noData, boolean empty) {
        super.onComplete(successs, noData, empty);
        if (null != mRefreshLayout) {
            mRefreshLayout.setRefreshing(false);
            if (noData) {
                mFooter.showNodata();
            } else {
                mFooter.showComplete();
            }
        }
    }
}
