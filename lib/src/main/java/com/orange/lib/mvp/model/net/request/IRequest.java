package com.orange.lib.mvp.model.net.request;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;

import java.sql.Wrapper;

public interface IRequest<N extends Wrapper> {
    /**
     * 网络请求
     *
     * @param wrapper 网络请求封装
     * @return 返回取消网络请求回调
     */
    INetCancel request(N wrapper);
}
