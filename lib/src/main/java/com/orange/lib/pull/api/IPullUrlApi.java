package com.orange.lib.pull.api;


import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.pull.callback.IPullNetCallback;

import java.lang.reflect.Type;
import java.util.Map;

public interface IPullUrlApi {

    /**
     * pull方式get网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel getPull(String url, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel getPull(Map<String, String> headers, String url, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式post网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel postPull(String url, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式post网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel postPull(Map<String, String> headers, String url, Map<String, String> params, Type type, IPullNetCallback<T> callback);
}
