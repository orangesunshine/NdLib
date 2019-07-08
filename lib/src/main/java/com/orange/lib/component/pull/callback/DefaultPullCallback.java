package com.orange.lib.component.pull.callback;

import android.view.View;

import com.orange.lib.pull.adapterpattern.PullNetCallbackAdapter;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.pull.request.IPageNetRequest;
import com.orange.lib.pull.callback.IPullNetCallback;
import com.orange.lib.utils.PageUtils;
import com.orange.lib.utils.ReflectionUtils;

import java.lang.reflect.Type;

public class DefaultPullCallback implements IPullCallback {
    protected Type mType;
    protected View mRefreshLayout;
    protected IPullNetCallback mNetCallback;
    protected IPageNetRequest mPageNetRequest;
    private boolean mIsRunning;

    public <T> DefaultPullCallback(IPageNetRequest<T> pageNetRequest, View refreshLayout, IPullNetCallback callback) {
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
    public INetCancel onPullRefresh() {
        if (mIsRunning) return null;
        mIsRunning = true;
        PageUtils.resetPageindexTag(mRefreshLayout);
        if (null != mPageNetRequest && null != mNetCallback)
            return mPageNetRequest.request(PageUtils.getPageindex(mRefreshLayout), mType, new PullNetCallbackAdapter(mNetCallback) {
                /**
                 * 完成
                 *
                 * @param successs
                 * @param noData
                 * @param emtpy
                 */
                @Override
                public void onComplete(boolean successs, boolean noData, boolean emtpy) {
                    super.onComplete(successs, noData, emtpy);
                    mIsRunning = false;
                }
            });
        return null;
    }

    /**
     * 上拉加载
     */
    @Override
    public INetCancel onPullLoadMore() {
        if (mIsRunning) return null;
        mIsRunning = true;
        PageUtils.pageIndexPlus(mRefreshLayout);
        if (null != mPageNetRequest && null != mNetCallback)
            return mPageNetRequest.request(PageUtils.getPageindex(mRefreshLayout), mType, new PullNetCallbackAdapter(mNetCallback) {
                /**
                 * 完成
                 *
                 * @param successs
                 * @param noData
                 * @param emtpy
                 */
                @Override
                public void onComplete(boolean successs, boolean noData, boolean emtpy) {
                    super.onComplete(successs, noData, emtpy);
                    mIsRunning = false;
                }
            });
        return null;
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
