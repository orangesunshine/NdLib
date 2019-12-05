package com.orange.thirdparty.retrofit;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.mvp.model.net.request.request.Params;
import com.orange.lib.mvp.model.net.request.request.Wrapper;
import com.orange.lib.utils.base.Preconditions;
import com.orange.lib.utils.text.TextUtils;
import com.orange.thirdparty.rxjava.parse.ResponseBodyParser;
import com.orange.utils.common.Collections;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.ResponseBody;

public class RetrofitWrapper<T> extends Wrapper<T, RetrofitParams> {
    public RetrofitWrapper(Builder builder) {
        super(builder);
    }

    // <editor-fold defaultstate="collapsed" desc="单个">
    public Observable<ResponseBody> observable() {
        List<? extends RetrofitParams> params = getParams();
        if (Collections.isEmpty(params)) return null;
        return params.get(0).observable();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="串行">
    public Observable<ResponseBody> serialObservable() {
        List<? extends RetrofitParams> params = getParams();
        if (Collections.isEmpty(params)) return null;
        LinkedList<? extends RetrofitParams> list = new LinkedList<>(params);
        if (Preconditions.isEmpty(list)) return null;
        try {
            return serial(list);
        } catch (IllegalAccessException e) {
            System.out.println("error: " + e.getMessage());
        }
        return null;
    }

    /**
     * 串行
     *
     * @param <T>
     * @return
     */
    public <T> Observable<ResponseBody> serial(LinkedList<? extends RetrofitParams> paramsList) throws IllegalAccessException {
        if (Preconditions.isNull(paramsList)) return null;
        if (paramsList.size() < 2) throw new IllegalAccessException("paramsList.size()<2");
        RetrofitParams poll = paramsList.poll();
        return poll.observable().flatMap(new io.reactivex.functions.Function<ResponseBody, ObservableSource<ResponseBody>>() {
            @Override
            public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Exception {
                RetrofitParams tempParams = paramsList.peek();
                ResponseBodyParser responseBodyParser = new ResponseBodyParser(responseBody, poll.getType());
                BaseResponse response = responseBodyParser.parseResponse();
                if (null != tempParams)
                    tempParams.flatMapConvert(response);
                if (paramsList.size() < 2) return tempParams.observable();
                return serial(paramsList);
            }
        });
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="并行">
    public Observable<ResponseBody> parallelObservable() {
        List<? extends RetrofitParams> params = getParams();
        if (Collections.isEmpty(params)) return null;
        return params.get(0).observable();
    }
// </editor-fold>

    public static class Builder<T> extends Wrapper.Builder<T> {
        public static Builder builder() {
            return new Builder();
        }

        public Builder<T> params(RetrofitParams params) {
            if (null == params) return this;
            deduplication(params);
            mParams.add(params);
            return this;
        }

        public Builder<T> callback(ICallback<T> callback) {
            mCallback = callback;
            return this;
        }

        protected void deduplication(RetrofitParams params) {
            if (null == params) return;
            String url = params.getUrl();
            if (TextUtils.isEmpty(url)) return;
            for (Params param : mParams) {
                if (null == param) {
                    mParams.remove(param);
                    continue;
                }
                if (url.equals(param.getUrl())) {
                    mParams.remove(param);
                    break;
                }
            }
        }

        @Override
        public RetrofitWrapper build() {
            return new RetrofitWrapper(this);
        }
    }
}
