package com.orange.lib.mvp.model.net.request.request;


import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.callback.loading.INetCallback;

public interface INetRequest<T> {
    INetCancel request(INetCallback<T> callback);
}
