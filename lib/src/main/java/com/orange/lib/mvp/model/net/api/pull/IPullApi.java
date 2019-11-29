package com.orange.lib.mvp.model.net.api.pull;


import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.callback.pull.IPullNetCallback;

import java.lang.reflect.Type;
import java.util.Map;

public interface IPullApi {

    /**
     * pull方式get网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel get(String url, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel get(Map<String, String> headers, String url, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式post网络请求
     *
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel post(String url, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式post网络请求，带请求头
     *
     * @param headers  请求头
     * @param url      全路径
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T> INetCancel post(Map<String, String> headers, String url, Map<String, String> params, Type type, IPullNetCallback<T> callback);
}
