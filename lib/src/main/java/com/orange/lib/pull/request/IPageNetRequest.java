package com.orange.lib.pull.request;


import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.pull.callback.IPullNetCallback;

import java.lang.reflect.Type;

public interface IPageNetRequest<T> {
    /**
     * 请求参数curPage网络
     *
     * @param pageIndex
     * @param callback
     */
    INetCancel request(int pageIndex, Type type, IPullNetCallback<T> callback);
}
