package com.orange.lib.loading.request;


import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.loading.callback.INetCallback;

public interface INetRequest<T> {
    INetCancel request(INetCallback<T> callback);
}
