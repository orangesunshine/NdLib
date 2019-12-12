package com.orange.lib.mvp.model.net.request.request;

import com.orange.lib.utils.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class Params {
    protected String mUrl;//网络请求地址
    protected Map<String, String> mParams = new HashMap<>();//请求参数
    protected Map<String, String> mHeaders = new HashMap<>();//请求头
    protected Method mMethod = Method.POST;//请求方式

    protected Params() {
    }

    protected Params(Builder builder) {
        mUrl = builder.mUrl;
        mParams = builder.mParams;
        mHeaders = builder.mHeaders;
        mMethod = builder.mMethod;
    }

    public Params addParams(String key, String value) {
        if (Preconditions.isNull(mParams)) return this;
        mParams.put(key, value);
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Method getMethod() {
        return mMethod;
    }


    @Override
    public String toString() {
        return "Params{" +
                "mUrl='" + mUrl + '\'' +
                ", mParams=" + mParams +
                ", mHeaders=" + mHeaders +
                ", mMethod=" + mMethod +
                '}';
    }

    ///////////////////////////////////////////////////////////////////////////
    // builder
    ///////////////////////////////////////////////////////////////////////////

    public static class Builder {
        protected String mUrl;//网络请求地址
        protected Map<String, String> mParams = new HashMap<>();//请求参数
        protected Map<String, String> mHeaders = new HashMap<>();//请求头
        protected Method mMethod;//请求方式

        public static Builder builder() {
            return new Builder();
        }

        public Builder url(String url) {
            mUrl = url;
            return this;
        }

        public Builder params(Map<String, String> params) {
            if (Preconditions.isEmpty(params)) return this;
            mParams.clear();
            mParams.putAll(params);
            return this;
        }

        public Builder addParams(String key, String value) {
            mParams.put(key, value);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public Builder method(Method method) {
            mMethod = method;
            return this;
        }

        public Params build() {
            return new Params(this);
        }
    }

    public enum Method {
        POST, GET
    }
}
