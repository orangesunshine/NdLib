package com.orange.thirdparty.retrofit.params;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.thirdparty.rxjava.parse.FlatMapConvert;

import java.lang.reflect.Type;
import java.util.Map;

public class SerialRetrofitParams extends RetrofitParams {
    protected FlatMapConvert mFlatMapConvert;

    public SerialRetrofitParams(Builder builder) {
        mFlatMapConvert = builder.mFlatMapConvert;
    }

    public void setFlatMapConvert(FlatMapConvert flatMapConvert) {
        mFlatMapConvert = flatMapConvert;
    }

    public void flatMapConvert(BaseResponse response) {
        if (null != mFlatMapConvert) {
            mFlatMapConvert.convert(response, this);
        }
    }


    public static class Builder extends RetrofitParams.Builder {
        protected FlatMapConvert mFlatMapConvert;

        public Builder convert(FlatMapConvert convert) {
            mFlatMapConvert = convert;
            return this;
        }

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

        @Override
        public SerialRetrofitParams build() {
            return new SerialRetrofitParams(this);
        }
    }
}
