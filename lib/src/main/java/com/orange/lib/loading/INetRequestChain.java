package com.orange.lib.loading;

import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;

public interface INetRequestChain<T> {
    /**
     * 网络请求链，请求获取数据、及后续处理（网络成功、失败、网络错误）
     *
     * @param requests
     */
    INetCancel chain(NetRequest... requests);//网络请求链
}
