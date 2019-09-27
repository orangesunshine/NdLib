package com.orange.lib.loading.request;

import com.orange.lib.loading.callback.INetCallback;

import java.util.Map;

public class NetRequest<T> {
    private String mUrl;//网络请求地址
    private Map<String, String> mParams;//请求参数
    private Map<String, String> mHeaders;//请求头
    private Method mMethod;//请求方式
    private INetCallback<T> mNetCallback;//网络请求回调

    public NetRequest(NetRequestBuild build) {
        mUrl = build.mUrl;
        mParams = build.mParams;
        mHeaders = build.mHeaders;
        mMethod = build.mMethod;
        mNetCallback = build.mNetCallback;
    }

    public static class NetRequestBuild<T> {
        private String mUrl;//网络请求地址
        private Map<String, String> mParams;//请求参数
        private Map<String, String> mHeaders;//请求头
        private Method mMethod;//请求方式
        private INetCallback<T> mNetCallback;//网络请求回调

        public NetRequestBuild setUrl(String url) {
            mUrl = url;
            return this;
        }

        public NetRequestBuild setParams(Map<String, String> params) {
            mParams = params;
            return this;
        }

        public NetRequestBuild setHeaders(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public NetRequestBuild setMethod(Method method) {
            mMethod = method;
            return this;
        }

        public NetRequestBuild setNetCallback(INetCallback<T> netCallback) {
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
