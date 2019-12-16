package com.orange.lib.mvp.model.net.callback.loading;

import com.orange.lib.mvp.view.loading.ILoading;

public abstract class LoadingCallback<T> extends AbsCallback<T> {
    private ILoading loading;

    public LoadingCallback(ILoading loading) {
        this.loading = loading;
    }

    @Override
    public void onStart() {
        if (null != loading)
            loading.showLoading();
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (null != loading)
            loading.dismissLoading();
    }
}
