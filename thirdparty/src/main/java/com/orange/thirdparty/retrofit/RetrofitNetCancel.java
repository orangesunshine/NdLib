package com.orange.thirdparty.retrofit;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;

import io.reactivex.disposables.Disposable;

public class RetrofitNetCancel implements INetCancel {
    private Disposable mDisposable;
    private String mUrl;

    public RetrofitNetCancel(Disposable disposable, String url) {
        mDisposable = disposable;
        mUrl = url;
    }

    @Override
    public void cancel(String url) {
        
        if (null != mDisposable && !mDisposable.isDisposed())
            mDisposable.dispose();
    }
}
