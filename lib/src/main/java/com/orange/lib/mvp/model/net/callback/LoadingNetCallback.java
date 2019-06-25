package com.orange.lib.mvp.model.net.callback;


import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;

public class LoadingNetCallback<T> extends DefaultNetCallback<T> {
    private ILoadingDialog loading;

    public LoadingNetCallback(ILoadingDialog loading) {
        this.loading = loading;
    }

    @Override
    public void onNetStart() {
        super.onNetStart();
        if (null != loading)
            loading.showLoadingDialog();
    }

    @Override
    public void onComplete(boolean successs, boolean noData, boolean empty) {
        super.onComplete(successs, noData, empty);
        if (null != loading)
            loading.dismissLoadingDialog();
    }
}
