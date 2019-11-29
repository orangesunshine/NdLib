package com.orange.lib.mvp.model.net.callback.pull;

import com.orange.lib.common.convert.IPullConvert;
import com.orange.lib.common.reponse.PullData;
import com.orange.lib.mvp.view.page.pull.IPullPage;

public class PagePullNetCallback<ITEM> implements IPullNetCallback<PullData<ITEM>> {
    private IPullPage mPageStatus;
    private IPullConvert<ITEM> mPullConvert;
    private LogPullNetCallback mLogPullNetCallback = new LogPullNetCallback();

    public PagePullNetCallback(IPullPage pageStatus, IPullConvert<ITEM> pullConvert) {
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
