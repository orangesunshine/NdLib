package com.orange.lib.mvp.model.net.pull;


import com.orange.lib.mvp.model.net.callback.INetCallback;

import java.lang.reflect.Type;

public interface IPageNetRequest<T> {
    /**
     * 请求参数curPage网络
     *
     * @param pageIndex
     * @param callback
     */
    void request(int pageIndex, Type type, INetCallback<T> callback);
}
