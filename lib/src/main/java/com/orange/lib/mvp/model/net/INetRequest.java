package com.orange.lib.mvp.model.net;


import com.orange.lib.mvp.model.net.callback.INetCallback;

public interface INetRequest<T> {
    void request(INetCallback<T> callback);
}
