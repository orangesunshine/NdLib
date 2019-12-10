package com.orange.lib.mvp.model.net.callback.loading;

/**
 * 网络回调抽象层
 *
 * @param <T>
 */
public interface ICallback<T> {
    /**
     * 网络请求开始
     */
    <T1> void onStart();

    /**
     * 成功
     *
     * @param t
     */
    void onSuccess(T t);

    /**
     * 网络请求完成
     */
    void onComplete();

    /**
     * 失败
     *
     * @param code
     * @param error
     */
    void onError(int code, Throwable error);

    void setOnCompleteListener(OnCompleteListener listener);
}

