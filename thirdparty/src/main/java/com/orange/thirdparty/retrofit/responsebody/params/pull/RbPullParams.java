package com.orange.thirdparty.retrofit.responsebody.params.pull;

import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.retrofit.responsebody.params.RbParams;

import java.util.Map;

import static com.orange.lib.constance.IConst.PAGE_SIZE;

public class RbPullParams extends RbParams {
    protected int mPageIndex = 1;
    protected int mPageSize = PAGE_SIZE;

    public RbPullParams(Builder builder) {
        super(builder);
    }

    public static class Builder extends RbParams.Builder {
        protected int mPageIndex = 1;
        protected int mPageSize = PAGE_SIZE;

        public static Builder builder() {
            return new Builder();
        }

        public Builder index(int pageIndex) {
            mPageIndex = pageIndex;
            return this;
        }

        public Builder pageSize(int pageSize) {
            mPageSize = pageSize;
            return this;
        }

        @Override
        public Builder url(String url) {
            mUrl = url;
            return this;
        }

        @Override
        public Builder params(Map<String, String> params) {
            if (Preconditions.isEmpty(params)) return this;
            mParams.clear();
            mParams.putAll(params);
            return this;
        }

        @Override
        public Builder addParams(String key, String value) {
            mParams.put(key, value);
            return this;
        }

        @Override
        public Builder headers(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        @Override
        public Builder method(Method method) {
            mMethod = method;
            return this;
        }

        @Override
        public RbPullParams build() {
            return new RbPullParams(this);
        }
    }
}
