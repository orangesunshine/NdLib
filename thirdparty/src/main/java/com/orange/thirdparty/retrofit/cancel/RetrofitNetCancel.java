package com.orange.thirdparty.retrofit.cancel;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;

import io.reactivex.disposables.Disposable;

public class RetrofitNetCancel implements INetCancel {
    private Disposable mDisposable;

    public RetrofitNetCancel(Disposable disposable) {
        mDisposable = disposable;
    }

    @Override
    public void cancel() {
        if (null != mDisposable && !mDisposable.isDisposed())
            mDisposable.dispose();
    }
}
