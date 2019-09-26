package com.orange.thirdparty.smartfreshlayout;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.orange.lib.common.reponse.PullData;
import com.orange.lib.component.recyclerview.CommonAdapter;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.pull.callback.IPullNetCallback;
import com.orange.lib.utils.pull.PageUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * SmartRefreshLayout实现INetCallback
 *
 * @param <ITEM>
 */
public class SmartRefreshNetCallback<ITEM> implements IPullNetCallback<PullData<ITEM>> {
    private SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected View mEmptyView;//页面为空显示
    protected int mItemLayoutId;//recyclerview的item项布局
    protected IConvertRecyclerView<ITEM> mConvertRecyclerView;//recyclerview的item项填充数据

    /**
     * 构造方法
     *
     * @param refreshLayout
     * @param recyclerView
     * @param emptyView
     * @param itemLayoutId
     * @param convertRecyclerView
     */
    public SmartRefreshNetCallback(SmartRefreshLayout refreshLayout, RecyclerView recyclerView, View emptyView, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        mRefreshLayout = refreshLayout;
        mRecyclerView = recyclerView;
        mEmptyView = emptyView;
        mItemLayoutId = itemLayoutId;
        mConvertRecyclerView = convertRecyclerView;
    }

    /**
     * 数据成功
     *
     * @param pullResponse
     */
    @Override
    public void onSuccess(PullData<ITEM> pullResponse) {
        CommonAdapter.adapterDatas(mRefreshLayout.getContext(), mRecyclerView, mEmptyView, mItemLayoutId, null == pullResponse ? null : pullResponse.getList(), PageUtils.isLoadmore(mRefreshLayout), mConvertRecyclerView);
    }

    /**
     * 网络请求完成
     *
     * @param successs
     * @param noData
     * @param empty
     */
    @Override
    public void onComplete(boolean successs, boolean noData, boolean empty) {
        if (null != mRefreshLayout) {
            if (noData) {
                mRefreshLayout.finishRefreshWithNoMoreData();
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        }
    }

    /**
     * 失败
     *
     * @param code
     * @param error
     */
    @Override
    public void onError(int code, Throwable error) {

    }
}
