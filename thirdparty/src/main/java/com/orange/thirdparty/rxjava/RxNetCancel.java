package com.orange.thirdparty.rxjava;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;

import io.reactivex.disposables.Disposable;

public class RxNetCancel implements INetCancel {
    private Disposable mDisposable;

    public RxNetCancel(Disposable disposable) {
        mDisposable = disposable;
    }

    @Override
    public void cancel() {
        if (null != mDisposable && !mDisposable.isDisposed())
            mDisposable.dispose();
    }
}
