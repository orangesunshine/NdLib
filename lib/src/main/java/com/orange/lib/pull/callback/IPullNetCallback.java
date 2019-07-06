package com.orange.lib.pull.callback;

/**
 * 网络回调抽象层
 *
 * @param <T>
 */
public interface IPullNetCallback<T> {

    /**
     * 成功
     *
     * @param t
     */
    void onSuccess(T t);

    /**
     * @param successs true:onSuccess,false:onError
     * @param noData   没有更多数据：用于pull
     * @param empty    结果数据为空
     */
    void onComplete(boolean successs, boolean noData, boolean empty);

    /**
     * 失败
     *
     * @param code
     * @param error
     */
    void onError(int code, Throwable error);
}
