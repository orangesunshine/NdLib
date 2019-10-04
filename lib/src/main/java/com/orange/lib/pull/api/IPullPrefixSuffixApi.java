package com.orange.lib.pull.api;

import com.orange.lib.common.reponse.PullData;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.pull.callback.IPullNetCallback;

import java.lang.reflect.Type;
import java.util.Map;

public interface IPullPrefixSuffixApi {
    /**
     * pull方式get网络请求
     *
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T extends PullData> INetCancel getPull(String prefix, String suffix, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式get网络请求，带请求头
     *
     * @param headers  请求头
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T extends PullData> INetCancel getPull(Map<String, String> headers, String prefix, String suffix, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式post网络请求
     *
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T extends PullData> INetCancel postPull(String prefix, String suffix, Map<String, String> params, Type type, IPullNetCallback<T> callback);

    /**
     * pull方式post网络请求，带请求头
     *
     * @param headers  请求头
     * @param params   参数
     * @param callback 回调
     * @param <T>      网络返回实体
     */
    <T extends PullData> INetCancel postPull(Map<String, String> headers, String prefix, String suffix, Map<String, String> params, Type type, IPullNetCallback<T> callback);
}
