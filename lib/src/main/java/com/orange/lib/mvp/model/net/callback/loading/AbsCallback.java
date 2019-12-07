package com.orange.lib.mvp.model.net.callback.loading;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.netcancel.NetCancelManager;

public abstract class AbsCallback<T> implements ICallback<T> {
    private INetCancel mINetCancel;

    @Override
    public void setINetCancel(INetCancel INetCancel) {
        mINetCancel = INetCancel;
    }

    @Override
    public void onComplete() {
        if (null != mINetCancel)
            NetCancelManager.getInstance().unregisterNetCancel(mINetCancel);
    }
}
