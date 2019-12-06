package com.orange.thirdparty.rxjava.params;

import com.orange.lib.mvp.model.net.request.request.Params;
import com.orange.lib.utils.base.Preconditions;
import com.orange.thirdparty.retrofit.RetrofitClient;
import com.orange.thirdparty.retrofit.api.IRetrofitApi;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class RxParams extends Params {
    public RxParams(Builder builder) {
        super(builder);
    }

    public static class Builder extends Params.Builder {
        public static Builder builder() {
            return new Builder();
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
        public Builder type(Type type) {
            mType = type;
            return this;
        }

        @Override
        public RxParams build() {
            return new RxParams(this);
        }
    }

    public Observable<ResponseBody> generateObservable() {
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
}
