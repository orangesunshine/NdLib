package com.orange.lib.loading.request;

import com.orange.lib.loading.callback.INetCallback;

import java.util.Map;

public class NetRequest<T> {
    private String mUrl;//网络请求地址
    private Map<String, String> mParams;//请求参数
    private Map<String, String> mHeaders;//请求头
    private Method mMethod;//请求方式
    private INetCallback<T> mNetCallback;//网络请求回调

    public void setNetCallback(INetCallback<T> netCallback) {
        mNetCallback = netCallback;
    }

    public NetRequest(NetBuilder build) {
        mUrl = build.mUrl;
        mParams = build.mParams;
        mHeaders = build.mHeaders;
        mMethod = build.mMethod;
        mNetCallback = build.mNetCallback;
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

    public INetCallback<T> getNetCallback() {
        return mNetCallback;
    }

    ///////////////////////////////////////////////////////////////////////////
    // builder
    ///////////////////////////////////////////////////////////////////////////

    public static class NetBuilder<T> {
        private String mUrl;//网络请求地址
        private Map<String, String> mParams;//请求参数
        private Map<String, String> mHeaders;//请求头
        private Method mMethod;//请求方式
        private INetCallback<T> mNetCallback;//网络请求回调

        public static NetBuilder builder() {
            return new NetBuilder();
        }

        public NetBuilder url(String url) {
            mUrl = url;
            return this;
        }

        public NetBuilder params(Map<String, String> params) {
            mParams = params;
            return this;
        }

        public NetBuilder headers(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public NetBuilder method(Method method) {
            mMethod = method;
            return this;
        }

        public NetBuilder callback(INetCallback<T> netCallback) {
            mNetCallback = netCallback;
            return this;
        }

        public NetRequest build() {
            return new NetRequest(this);
        }
    }

    public enum Method {
        POST, GET
    }
}
