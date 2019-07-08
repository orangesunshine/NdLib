package com.orange.lib.pull.callback;

import com.orange.lib.common.convert.IPullConvert;
import com.orange.lib.common.reponse.PullData;
import com.orange.lib.pull.pagestatus.IPullPageStatus;

public class PageStatusPullNetCallback<ITEM> implements IPullNetCallback<PullData<ITEM>> {
    private IPullPageStatus mPageStatus;
    private IPullConvert<ITEM> mPullConvert;
    private LogPullNetCallback mLogPullNetCallback = new LogPullNetCallback();

    public PageStatusPullNetCallback(IPullPageStatus pageStatus, IPullConvert<ITEM> pullConvert) {
        mPageStatus = pageStatus;
        mPullConvert = pullConvert;
    }

    /**
     * 成功
     */
    @Override
    public void onSuccess(PullData<ITEM> pullResponse) {
        if (null != mLogPullNetCallback)
            mLogPullNetCallback.onSuccess(pullResponse);
        if (null != mPullConvert)
            mPullConvert.convert(pullResponse);
    }

    /**
     * @param successs true:onSuccess,false:onError
     * @param noData   没有更多数据：用于pull
     * @param empty    结果数据为空
     */
    @Override
    public void onComplete(boolean successs, boolean noData, boolean empty) {
        if (null != mLogPullNetCallback)
            mLogPullNetCallback.onComplete(successs, noData, empty);
        if (null == mPageStatus) return;
        if (!successs) {
            mPageStatus.showError();
        } else if (empty) {
            mPageStatus.showEmpty();
        } else {
            mPageStatus.showContent();
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
        if (null != mLogPullNetCallback)
            mLogPullNetCallback.onError(code, error);
    }
}
