package com.orange.lib.mvp.model.net.callback.loading;

import com.orange.lib.common.config.Config;
import com.orange.lib.component.toast.Toasts;
import com.orange.utils.selected.Throwables;

public abstract class AbsCallback<T> implements ICallback<T> {
    protected OnCompleteListener mOnCompleteListener;

    @Override
    public void setOnCompleteListener(OnCompleteListener listener) {
        mOnCompleteListener = listener;
    }

    @Override
    public void onError(int code, Throwable error) {
        Config.getInstance().getLog().e(error);
        Toasts.showError(Throwables.getFullStackTrace(error));
    }

    @Override
    public void onComplete() {
        if (null != mOnCompleteListener)
            mOnCompleteListener.onComplete();
    }
}
