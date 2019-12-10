package com.orange.thirdparty.rxjava.params;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.rxjava.parse.FlatMapConvert;
import com.orange.thirdparty.rxjava.parse.RxParser;

import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * @param <T> 上一个请求返回类型
 */
public class RxSerialParams<T> extends RxParams {
    protected FlatMapConvert mFlatMapConvert;

    public RxSerialParams(Builder builder) {
        super(builder);
        mFlatMapConvert = builder.mFlatMapConvert;
    }

    public void flatMapConvert(ResponseBody responseBody) {
        if (null != mFlatMapConvert) mFlatMapConvert.convert(parse(responseBody), this);
    }

    public <T> void flatMapConvert(T response) {
        if (null != mFlatMapConvert) mFlatMapConvert.convert(response, this);
    }

    public BaseResponse parse(ResponseBody responseBody) {
        return RxParser.parse(responseBody, mType);
    }

    public FlatMapConvert<BaseResponse<T>> getFlatMapConvert() {
        return mFlatMapConvert;
    }

    public static class Builder<T> extends RxParams.Builder {
        protected FlatMapConvert<T> mFlatMapConvert;

        public static Builder builder() {
            return new Builder();
        }

        public Builder flatMapConvert(FlatMapConvert<T> flatMapConvert) {
            mFlatMapConvert = flatMapConvert;
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
