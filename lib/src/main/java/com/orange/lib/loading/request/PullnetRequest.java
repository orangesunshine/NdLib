package com.orange.lib.loading.request;

import static com.orange.lib.constance.IConst.PULL_ITEM_COUNT;

/**
 * @Author: orange
 * @CreateDate: 2019/10/7 10:53
 */
public class PullnetRequest<T> extends NetRequest<T> {
    protected int mPageIndex;
    protected int mRequestCount;

    public PullnetRequest(PullnetRequestBuilder build) {
        super(build);
        mPageIndex = build.mPageIndex;
        mRequestCount = build.mRequestCount;
    }

    public static class PullnetRequestBuilder<T> extends NetBuilder<T> {
        protected int mPageIndex;
        protected int mRequestCount = PULL_ITEM_COUNT;
        public static NetBuilder builder() {
            return new PullnetRequestBuilder();
        }

        public NetBuilder index(int pageIndex) {
            mPageIndex = pageIndex;
            return this;
        }

        public NetBuilder count(int count) {
            mRequestCount = count;
            return this;
        }
    }
}
