package com.orange.lib.mvp.model.net.callback.loading;

public abstract class AbsCallback<T> implements ICallback<T> {
    protected OnCompleteListener mOnCompleteListener;

    @Override
    public void setOnCompleteListener(OnCompleteListener listener) {
        mOnCompleteListener = listener;
    }

    @Override
    public void onComplete() {
        if (null != mOnCompleteListener)
            mOnCompleteListener.onComplete();
    }
}
