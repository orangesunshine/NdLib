package com.orange.lib.mvp.model.net.callback.pull;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.common.convert.IPullConvert;
import com.orange.lib.common.reponse.PullData;

public class SwipePullNetCallback<ITEM> implements IPullNetCallback<PullData<ITEM>> {
    private LogPullNetCallback mLogPullNetCallback = new LogPullNetCallback();
    private IPullConvert<ITEM> mPullConvert;
    private SwipeRefreshLayout mRefreshLayout;

    public SwipePullNetCallback(SwipeRefreshLayout refreshLayout, IPullConvert<ITEM> pullConvert) {
        mRefreshLayout = refreshLayout;
        mPullConvert = pullConvert;
    }

    @Override
    public void onSuccess(PullData<ITEM> pullResponse) {
        if (null != mLogPullNetCallback)
            mLogPullNetCallback.onSuccess(pullResponse);
        if (null != mPullConvert)
            mPullConvert.convert(pullResponse);
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
