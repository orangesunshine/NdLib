package com.orange.lib.mvp.model.net.callback.loading;

public abstract class AbsCallback<T> implements ICallback<T> {
    private OnCompleteListener mOnCompleteListener;

    @Override
    public void setOnComplteListener(OnCompleteListener listener) {
        mOnCompleteListener = listener;
    }

    @Override
    public void removeOnCompleteListener() {
        mOnCompleteListener = null;
    }

    @Override
    public void onComplete() {
        if (null != mOnCompleteListener)
            mOnCompleteListener.onComplete();
    }
}
