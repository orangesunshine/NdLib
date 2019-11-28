package com.orange.lib.common.convert;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.common.reponse.PullData;
import com.orange.lib.component.recyclerview.CommonAdapter;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.utils.pull.PageUtils;

public class PullConvert<ITEM> implements IPullConvert<ITEM> {
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected View mEmptyView;
    protected int mItemLayoutId;
    protected IConvertRecyclerView<ITEM> mConvertRecyclerView;

    public PullConvert(IHolder holder, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        this(holder.getView(R.id.id_refreshlayout_orange), holder.getView(R.id.id_recyclerview_orange), holder.getView(R.id.id_empty_orange), itemLayoutId, convertRecyclerView);
    }

    public PullConvert(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, View emptyView, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        mEmptyView = emptyView;
        mItemLayoutId = itemLayoutId;
        mConvertRecyclerView = convertRecyclerView;
    }

    @Override
    public void convert(PullData<ITEM> pullResponse) {
        CommonAdapter.adapterDatas(mRefreshLayout.getContext(), mRecyclerView, mEmptyView, mItemLayoutId, null == pullResponse ? null : pullResponse.getList(), PageUtils.isLoadmore(mRefreshLayout), mConvertRecyclerView);
    }
}
