package com.orange.lib.mvp.model.net.request;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.request.request.NetRequestParams;

public interface IRequest {
    /**
     * 网络请求
     *
     * @param netRequestParams 网络请求封装
     * @return 返回取消网络请求回调
     */
    <T> INetCancel request(NetRequestParams<T> netRequestParams);

    /**
     * 并行网络请求
     *
     * @param netRequestParams 网络请求封装
     * @return 返回取消网络请求回调
     */
    <T extends NetRequestParams<K>, K> INetCancel parallel(T... netRequestParams);

    /**
     * 串行网络请求
     *
     * @param netRequestParams 网络请求封装
     * @return 返回取消网络请求回调
     */
    <T extends NetRequestParams<K>, K> INetCancel serial(T... netRequestParams);
}
