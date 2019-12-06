package com.orange.lib.mvp.model.net.request.request;

import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;

/**
 * 网络请求类
 */
public class Wrapper<N extends Params> {
    protected N mParams;
    protected int mState = IConst.STATE_NONE;
    protected ICallback mCallback;

    protected Wrapper(Builder<N> builder) {
        mState = IConst.STATE_BUILD;
        mParams = builder.mParams;
        mCallback = builder.mCallback;
    }

    public void callback(ICallback callback) {
        mCallback = callback;
    }

    public void state(int state) {
        mState = state;
    }

    public N getParams() {
        return mParams;
    }

    public int getState() {
        return mState;
    }

    public ICallback getCallback() {
        return mCallback;
    }

    public static class Builder<N extends Params> {
        protected N mParams;
        protected ICallback mCallback;

        public static Builder builder() {
            return new Builder();
        }

        public Wrapper build() {
            return new Wrapper(this);
        }

        public Builder params(N params) {
            if (null == params) return this;
            mParams = params;
            return this;
        }

        public Builder callback(ICallback callback) {
            mCallback = callback;
            return this;
        }

        public N getParams() {
            return mParams;
        }

        public ICallback getCallback() {
            return mCallback;
        }
    }
}
