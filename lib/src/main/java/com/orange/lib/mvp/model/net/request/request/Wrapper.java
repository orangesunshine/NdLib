package com.orange.lib.mvp.model.net.request.request;

import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络请求类
 *
 * @param <T>
 */
public class Wrapper<T, N extends Params> {
    protected List<N> mParams = new ArrayList<>();
    protected int mState = IConst.STATE_NONE;
    protected ICallback<T> mCallback;

    protected Wrapper(Builder builder) {
        mState = IConst.STATE_BUILD;
        mParams.clear();
        mParams.addAll(builder.mParams);
        mCallback = builder.mCallback;
    }

    public void callback(ICallback callback) {
        mCallback = callback;
    }

    public void state(int state) {
        mState = state;
    }

    public List<N> getParams() {
        return mParams;
    }

    public int getState() {
        return mState;
    }

    public ICallback<T> getCallback() {
        return mCallback;
    }

    public static class Builder<T> {
        protected List<Params> mParams = new ArrayList<>();
        protected ICallback<T> mCallback;

        public static Builder builder() {
            return new Builder();
        }

        public Wrapper build() {
            return new Wrapper(this);
        }

        public Builder<T> params(Params params) {
            if (null == params) return this;
            deduplication(params);
            mParams.add(params);
            return this;
        }

        public Builder<T> callback(ICallback<T> callback) {
            mCallback = callback;
            return this;
        }

        protected void deduplication(Params params) {
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

        public List<Params> getParams() {
            return mParams;
        }

        public ICallback<T> getCallback() {
            return mCallback;
        }
    }
}
