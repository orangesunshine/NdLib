package com.orange.lib.loading.api;


import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.loading.callback.INetCallback;

import java.util.Map;

public interface IUrlApi {
    /**
     * loading方式get网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel get(String url, Map<String, String> params, INetCallback<T> callback);

    /**
     * loading方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel get(Map<String, String> headers, String url, Map<String, String> params, INetCallback<T> callback);

    /**
     * loading方式post网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel post(String url, Map<String, String> params, INetCallback<T> callback);

    /**
     * loading方式post网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel post(Map<String, String> headers, String url, Map<String, String> params, INetCallback<T> callback);
}
