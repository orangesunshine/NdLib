package com.orange.lib.mvp.model.net.request.request;

import com.orange.lib.mvp.model.net.callback.loading.INetCallback;

import java.lang.reflect.Type;
import java.util.Map;

public class NetRequestParams<T> {
    private String mUrl;//网络请求地址
    private Map<String, String> mParams;//请求参数
    private Map<String, String> mHeaders;//请求头
    private Method mMethod;//请求方式
    private Type mType;//解析泛型
    private INetCallback<T> mNetCallback;//网络请求回调

    public void setNetCallback(INetCallback<T> netCallback) {
        mNetCallback = netCallback;
    }

    protected NetRequestParams(NetRequestParamsBuilder builder) {
        mUrl = builder.mUrl;
        mParams = builder.mParams;
        mHeaders = builder.mHeaders;
        mMethod = builder.mMethod;
        mType = builder.mType;
        mNetCallback = builder.mNetCallback;
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

    public INetCallback<T> getNetCallback() {
        return mNetCallback;
    }

    ///////////////////////////////////////////////////////////////////////////
    // builder
    ///////////////////////////////////////////////////////////////////////////

    public static class NetRequestParamsBuilder<T> {
        private String mUrl;//网络请求地址
        private Map<String, String> mParams;//请求参数
        private Map<String, String> mHeaders;//请求头
        private Method mMethod;//请求方式
        private Type mType;//解析泛型
        private INetCallback<T> mNetCallback;//网络请求回调

        public static NetRequestParamsBuilder builder() {
            return new NetRequestParamsBuilder();
        }

        public NetRequestParamsBuilder url(String url) {
            mUrl = url;
            return this;
        }

        public NetRequestParamsBuilder params(Map<String, String> params) {
            mParams = params;
            return this;
        }

        public NetRequestParamsBuilder headers(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public NetRequestParamsBuilder method(Method method) {
            mMethod = method;
            return this;
        }

        public NetRequestParamsBuilder callback(INetCallback<T> netCallback) {
            mNetCallback = netCallback;
            return this;
        }

        public NetRequestParamsBuilder callback(Type type) {
            mType = type;
            return this;
        }

        public NetRequestParams build() {
            return new NetRequestParams(this);
        }
    }

    public enum Method {
        POST, GET
    }
}
