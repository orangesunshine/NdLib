package com.orange.lib.component.pull.callback;

import android.view.View;

import com.orange.lib.mvp.model.net.callback.INetCallback;
import com.orange.lib.mvp.model.net.pull.IPageNetRequest;
import com.orange.lib.utils.PageUtils;
import com.orange.lib.utils.ReflectionUtils;

import java.lang.reflect.Type;

public class DefaultPullCallback implements IPullCallback {
    protected Type mType;
    protected View mRefreshLayout;
    protected INetCallback mNetCallback;
    protected IPageNetRequest mPageNetRequest;

    public <T> DefaultPullCallback(View refreshLayout, IPageNetRequest<T> pageNetRequest, INetCallback callback) {
        mType = ReflectionUtils.pageNetRequestGenericType(pageNetRequest);
        mPageNetRequest = pageNetRequest;
        mNetCallback = callback;
        mRefreshLayout = refreshLayout;
        PageUtils.resetPageindexTag(refreshLayout);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onPullRefresh() {
        PageUtils.resetPageindexTag(mRefreshLayout);
        if (null != mPageNetRequest && null != mNetCallback)
            mPageNetRequest.request(PageUtils.getPageindex(mRefreshLayout), mType, mNetCallback);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onPullLoadMore() {
        PageUtils.pageIndexPlus(mRefreshLayout);
        if (null != mPageNetRequest && null != mNetCallback)
            mPageNetRequest.request(PageUtils.getPageindex(mRefreshLayout), mType, mNetCallback);
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
