package com.orange.lib.loading.api;


import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;

public interface IApi {

    /**
     * 网络请求
     *
     * @param netRequest 网络请求封装
     * @return 返回取消网络请求回调
     */
    <T> INetCancel single(NetRequest<T> netRequest);

    /**
     * 网络请求
     *
     * @param netRequests 网络请求封装
     * @return 返回取消网络请求回调
     */
    <T extends NetRequest<K>, K> INetCancel multiply(T... netRequests);
}
