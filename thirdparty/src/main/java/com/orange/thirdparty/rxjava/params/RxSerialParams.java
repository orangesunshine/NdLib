package com.orange.thirdparty.rxjava.params;

import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.rxjava.parse.RxMapper;

import java.util.Map;

public class RxSerialParams extends RxParams {
    protected RxMapper mRxMapper;

    public RxSerialParams(Builder builder) {
        super(builder);
        mRxMapper = builder.mRxMapper;
        mRxMapper.setGenerate(this);
    }

    public RxMapper getRxMapper() {
        return mRxMapper;
    }

    public static class Builder extends RxParams.Builder {
        protected RxMapper mRxMapper;

        public static Builder builder() {
            return new Builder();
        }

        public Builder flatMapConvert(RxMapper rxConvert) {
            mRxMapper = Preconditions.needNotNull(rxConvert);
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
        public RxSerialParams build() {
            return new RxSerialParams(this);
        }

    }
}
