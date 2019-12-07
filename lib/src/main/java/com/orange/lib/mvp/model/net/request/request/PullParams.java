package com.orange.lib.mvp.model.net.request.request;

import static com.orange.lib.constance.IConst.PAGE_SIZE;

/**
 * @Author: orange
 * @CreateDate: 2019/10/7 10:53
 */
public class PullParams extends Params {
    protected int mPageIndex;
    protected int mRequestCount;

    public PullParams(PullnetRequestRequestParamsBuilder build) {
        super(build);
        mPageIndex = build.mPageIndex;
        mRequestCount = build.mRequestCount;
    }

    public static class PullnetRequestRequestParamsBuilder extends Builder {
        protected int mPageIndex;
        protected int mRequestCount = PAGE_SIZE;

        public static Builder builder() {
            return new PullnetRequestRequestParamsBuilder();
        }

        public Builder index(int pageIndex) {
            mPageIndex = pageIndex;
            return this;
        }

        public Builder count(int count) {
            mRequestCount = count;
            return this;
        }
    }
}
