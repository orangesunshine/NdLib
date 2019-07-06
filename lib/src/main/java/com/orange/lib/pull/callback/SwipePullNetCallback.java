package com.orange.lib.pull.callback;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.common.reponse.PullData;
import com.orange.lib.component.recyclerview.CommonAdapter;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.utils.PageUtils;

public class SwipePullNetCallback<ITEM> implements IPullNetCallback<PullData<ITEM>> {
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected View mEmptyView;
    protected int mItemLayoutId;
    protected IConvertRecyclerView<ITEM> mConvertRecyclerView;
    private LogPullNetCallback mLogPullNetCallback = new LogPullNetCallback();

    public SwipePullNetCallback(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, View emptyView, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        this.mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        mEmptyView = emptyView;
        mItemLayoutId = itemLayoutId;
        mConvertRecyclerView = convertRecyclerView;
    }

    @Override
    public void onSuccess(PullData<ITEM> pullResponse) {
        if (null != mLogPullNetCallback)
            mLogPullNetCallback.onSuccess(pullResponse);
        CommonAdapter.adapterDatas(mRefreshLayout.getContext(), mRecyclerView, mEmptyView, mItemLayoutId, null == pullResponse ? null : pullResponse.getList(), PageUtils.isLoadmore(mRefreshLayout), mConvertRecyclerView);
    }

    @Override
    public void onComplete(boolean successs, boolean noData, boolean empty) {
        if (null != mLogPullNetCallback)
            mLogPullNetCallback.onComplete(successs, noData, empty);
        if (null != mRefreshLayout)
            mRefreshLayout.setRefreshing(false);
    }

    /**
     * 失败
     *
     * @param code
     * @param error
     */
    @Override
    public void onError(int code, Throwable error) {
        if (null != mLogPullNetCallback)
            mLogPullNetCallback.onError(code, error);
    }
}
