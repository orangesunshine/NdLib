package com.orange.lib.pull.callback;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.common.reponse.PullData;
import com.orange.lib.component.recyclerview.CommonAdapter;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.pull.pagestatus.IPullPageStatus;
import com.orange.lib.utils.PageUtils;

public class PageStatusPullNetCallback<ITEM> implements IPullNetCallback<PullData<ITEM>> {
    private IPullPageStatus mPageStatus;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected View mEmptyView;
    protected int mItemLayoutId;
    protected IConvertRecyclerView<ITEM> mConvertRecyclerView;
    private LogPullNetCallback mLogPullNetCallback = new LogPullNetCallback();

    public PageStatusPullNetCallback(IPullPageStatus pageStatus, SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView, LogPullNetCallback logPullNetCallback) {
        mPageStatus = pageStatus;
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        mItemLayoutId = itemLayoutId;
        mConvertRecyclerView = convertRecyclerView;
        mLogPullNetCallback = logPullNetCallback;
    }

    public PageStatusPullNetCallback(IPullPageStatus pageStatus) {
        mPageStatus = pageStatus;
    }

    /**
     * 成功
     *
     * @param t
     */
    @Override
    public void onSuccess(PullData<ITEM> pullResponse) {
        if (null != mLogPullNetCallback)
            mLogPullNetCallback.onSuccess(pullResponse);
        CommonAdapter.adapterDatas(mRefreshLayout.getContext(), mRecyclerView, mEmptyView, mItemLayoutId, null == pullResponse ? null : pullResponse.getList(), PageUtils.isLoadmore(mRefreshLayout), mConvertRecyclerView);
    }

    /**
     * @param successs true:onSuccess,false:onError
     * @param noData   没有更多数据：用于pull
     * @param empty    结果数据为空
     */
    @Override
    public void onComplete(boolean successs, boolean noData, boolean empty) {

    }

    /**
     * 失败
     *
     * @param code
     * @param error
     */
    @Override
    public void onError(int code, Throwable error) {
        if (null != mPageStatus)
            mPageStatus.showError();
    }
}
