package com.orange.thirdparty.rxjava.params;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.rxjava.parse.RxConvert;
import com.orange.thirdparty.rxjava.parse.RxParser;

import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.ResponseBody;

public class RxSerialParams extends RxParams {
    protected RxConvert mRxConvert;

    public RxSerialParams(Builder builder) {
        super(builder);
        mRxConvert = builder.mRxConvert;
    }

    /**
     * 上一个请求返回ResponseBody
     *
     * @param responseBody
     */
    public void flatMapConvert(ResponseBody responseBody) {
        if (null != mRxConvert) mRxConvert.convert(parse(responseBody), this);
    }

    /**
     * 上一个请求返回T
     *
     * @param response
     * @param <T>
     */
    public <T> void flatMapConvert(T response) {
        if (null != mRxConvert) mRxConvert.convert(response, this);
    }

    public BaseResponse parse(ResponseBody responseBody) {
        return RxParser.parse(responseBody, mType);
    }

    public RxConvert getRxConvert() {
        return mRxConvert;
    }

    public static class Builder<T> extends RxParams.Builder {
        protected RxConvert<T> mRxConvert;

        public static Builder builder() {
            return new Builder();
        }

        public Builder flatMapConvert(RxConvert<T> rxConvert) {
            mRxConvert = rxConvert;
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
        public RxSerialParams build(Type type) {
            mType = type;
            return new RxSerialParams(this);
        }

        @Override
        public RxSerialParams build() {
            return new RxSerialParams(this);
        }

    }
}
