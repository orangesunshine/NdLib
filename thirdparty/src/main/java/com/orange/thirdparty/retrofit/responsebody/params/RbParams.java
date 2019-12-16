package com.orange.thirdparty.retrofit.responsebody.params;

import com.orange.lib.mvp.model.net.request.request.Params;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.retrofit.client.RetrofitClient;
import com.orange.thirdparty.retrofit.api.IRetrofitApi;
import com.orange.thirdparty.retrofit.responsebody.params.generate.IGenObservable;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class RbParams extends Params implements IGenObservable<ResponseBody> {
    public RbParams(Builder builder) {
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
        public RbParams build() {
            return new RbParams(this);
        }
    }

    @Override
    public RbParams addParams(String key, String value) {
        if (Preconditions.isNull(mParams)) return this;
        mParams.put(key, value);
        return this;
    }

    @Override
    public Observable<ResponseBody> getObservable() {
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
