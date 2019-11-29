package com.orange.lib.mvp.model.net.request.request;

import static com.orange.lib.constance.IConst.PULL_ITEM_COUNT;

/**
 * @Author: orange
 * @CreateDate: 2019/10/7 10:53
 */
public class PullRequestParams<T> extends NetRequestParams<T> {
    protected int mPageIndex;
    protected int mRequestCount;

    public PullRequestParams(PullnetRequestRequestParamsBuilder build) {
        super(build);
        mPageIndex = build.mPageIndex;
        mRequestCount = build.mRequestCount;
    }

    public static class PullnetRequestRequestParamsBuilder<T> extends NetRequestParamsBuilder<T> {
        protected int mPageIndex;
        protected int mRequestCount = PULL_ITEM_COUNT;

        public static NetRequestParamsBuilder builder() {
            return new PullnetRequestRequestParamsBuilder();
        }

        public NetRequestParamsBuilder index(int pageIndex) {
            mPageIndex = pageIndex;
            return this;
        }

        public NetRequestParamsBuilder count(int count) {
            mRequestCount = count;
            return this;
        }
    }
}
