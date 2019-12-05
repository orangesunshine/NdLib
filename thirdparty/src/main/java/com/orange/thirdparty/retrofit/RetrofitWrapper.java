package com.orange.thirdparty.retrofit;

import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.mvp.model.net.request.request.Params;
import com.orange.lib.mvp.model.net.request.request.Wrapper;
import com.orange.lib.utils.text.TextUtils;
import com.orange.utils.common.Collections;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
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
        Observable<ResponseBody> observable = null;
        RetrofitParams element = list.poll();
        if (null != element) element.serial(list);
        return observable;
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
