package com.orange.thirdparty.retrofit;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.request.request.Params;
import com.orange.lib.utils.base.Preconditions;
import com.orange.thirdparty.rxjava.parse.FlatMapConvert;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class RetrofitParams extends Params {
    public RetrofitParams(Builder builder) {
        super(builder);
        mFlatMapConvert = builder.mFlatMapConvert;
    }

    private FlatMapConvert mFlatMapConvert;

    public void setFlatMapConvert(FlatMapConvert flatMapConvert) {
        mFlatMapConvert = flatMapConvert;
    }

    public Observable<ResponseBody> observable() {
        Params.Method method = getMethod();
        if (Preconditions.isNull(method)) method = Params.Method.POST;
        String url = getUrl();
        Map<String, String> params = getParams();
        Map<String, String> headers = getHeaders();
        Observable<ResponseBody> observable;
        if (Params.Method.GET == method) {
            observable = get(url, params, headers);
        } else if (Params.Method.POST == method) {
            observable = post(url, params, headers);
        } else {
            throw new IllegalArgumentException();
        }
        return observable;
    }

    public void flatMapConvert(BaseResponse response) {
        if (null != mFlatMapConvert) {
            mFlatMapConvert.convert(response, this);
        }
    }

    /**
     * loading方式post网络请求
     *
     * @param url     全路径
     * @param params  参数
     * @param headers 请求头
     */
    public <T> Observable<ResponseBody> post(String url, Map<String, String> params, Map<String, String> headers) {
        Observable<ResponseBody> observable;
        IRetrofitApi api = RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class);
        if (Preconditions.isEmpty(headers) && Preconditions.isEmpty(params)) {
            observable = api.post(url);
        } else if (Preconditions.isEmpty(headers)) {
            observable = api.post(url, params);
        } else {
            observable = api.post(url, params, headers);
        }
        return observable;
    }

    /**
     * loading方式get网络请求，带请求头
     *
     * @param headers 请求头
     * @param url     全路径
     * @param params  参数
     */
    public <T> Observable<ResponseBody> get(String url, Map<String, String> params, Map<String, String> headers) {
        Observable<ResponseBody> observable;
        IRetrofitApi api = RetrofitClient.getRetrofitInstance().create(IRetrofitApi.class);
        if (Preconditions.isEmpty(headers) && Preconditions.isEmpty(params)) {
            observable = api.get(url);
        } else if (Preconditions.isEmpty(headers)) {
            observable = api.get(url, params);
        } else {
            observable = api.get(url, params, headers);
        }
        return observable;
    }

    public static class Builder extends Params.Builder {
        private FlatMapConvert mFlatMapConvert;

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
        public RetrofitParams build() {
            return new RetrofitParams(this);
        }
    }
}
