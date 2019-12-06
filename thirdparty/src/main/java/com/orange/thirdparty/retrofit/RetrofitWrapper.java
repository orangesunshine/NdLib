package com.orange.thirdparty.retrofit;

import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.mvp.model.net.request.request.Wrapper;
import com.orange.thirdparty.retrofit.generate.IGenerate;

import io.reactivex.Observable;

public class RetrofitWrapper<T> extends Wrapper<AbsRetrofitParams> implements IGenerate<T> {
    public RetrofitWrapper(Builder retrofitWrapperBuilder) {
        super(retrofitWrapperBuilder);
    }

    @Override
    public Observable<T> generateObservable() {
        if (null == mParams) return null;
        return mParams.generateObservable();
    }

    public static class Builder extends Wrapper.Builder<AbsRetrofitParams> {
        public static Builder builder() {
            return new Builder();
        }

        public Builder params(AbsRetrofitParams params) {
            if (null == params) return this;
            mParams = params;
            return this;
        }

        public Builder callback(ICallback callback) {
            mCallback = callback;
            return this;
        }

        @Override
        public RetrofitWrapper build() {
            return new RetrofitWrapper(this);
        }
    }
}
