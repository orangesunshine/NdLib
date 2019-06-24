package com.orange.lib.component.pull.callback;

import android.view.View;


import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.model.net.callback.INetCallback;
import com.orange.lib.mvp.model.net.pull.IPageNetRequest;

import java.lang.reflect.Type;

public class AbstractPull<REFRESH extends View> implements IPullCallback {
    protected Type mType;
    protected int mPageIndex;
    protected REFRESH refreshLayout;
    protected RecyclerView recyclerView;
    protected INetCallback mNetCallback;
    protected IPageNetRequest mPageNetRequest;

    public <T> AbstractPull(IHolder holder, IPageNetRequest<T> pageRequest) {
        refreshLayout = holder.getView(R.id.refreshlayout);
        recyclerView = holder.getView(R.id.recyclerview);
        mPageNetRequest = pageRequest;
    }

    public <T> AbstractPull(REFRESH refreshLayout, RecyclerView recyclerView, IPageNetRequest<T> pageRequest) {
        this.refreshLayout = refreshLayout;
        this.recyclerView = recyclerView;
        mPageNetRequest = pageRequest;
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onPullRefresh() {
        Preconditions.checkNotNull(mPageNetRequest, mNetCallback);
        mPageIndex = 1;
        mPageNetRequest.request(mPageIndex, mType, mNetCallback);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onPullLoadMore() {
        Preconditions.checkNotNull(mPageNetRequest, mNetCallback);
        mPageIndex++;
        mPageNetRequest.request(mPageIndex, mType, mNetCallback);
    }

    /**
     * 获取当前页数
     *
     * @return
     */
    @Override
    public int getCurPage() {
        return mPageIndex;
    }
}
