package com.orange.lib.mvp.model.net.request.request;

import com.orange.lib.utils.base.Preconditions;

import java.lang.reflect.Type;
import java.util.Map;

public class Params {
    private String mUrl;//网络请求地址
    private Map<String, String> mParams;//请求参数
    private Map<String, String> mHeaders;//请求头
    private Method mMethod;//请求方式
    private Type mType;//解析泛型

    protected Params(Builder builder) {
        mUrl = builder.mUrl;
        mParams = builder.mParams;
        mHeaders = builder.mHeaders;
        mMethod = builder.mMethod;
        mType = builder.mType;
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

    public Type getType() {
        return mType;
    }

    @Override
    public String toString() {
        return "Params{" +
                "mUrl='" + mUrl + '\'' +
                ", mParams=" + mParams +
                ", mHeaders=" + mHeaders +
                ", mMethod=" + mMethod +
                ", mType=" + mType +
                '}';
    }

    ///////////////////////////////////////////////////////////////////////////
    // builder
    ///////////////////////////////////////////////////////////////////////////

    public static class Builder {
        protected String mUrl;//网络请求地址
        protected Map<String, String> mParams;//请求参数
        protected Map<String, String> mHeaders;//请求头
        protected Method mMethod;//请求方式
        protected Type mType;//解析泛型

        public static Builder builder() {
            return new Builder();
        }

        public Builder url(String url) {
            mUrl = url;
            return this;
        }

        public Builder params(Map<String, String> params) {
            mParams = params;
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

        public Builder type(Type type) {
            mType = type;
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
