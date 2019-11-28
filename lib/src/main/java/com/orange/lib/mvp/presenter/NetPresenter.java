package com.orange.lib.mvp.presenter;

import com.orange.lib.common.config.Config;
import com.orange.lib.loading.api.IApi;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.utils.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:54
 */
public class NetPresenter<V extends INetContact.View> extends BasePresenter<V> implements IApi {
    protected IApi mUrlApi = Config.getInstance().getNet();
    protected Map<String, INetCancel> cancelMap = new HashMap<>();//缓存网络请求接口，用于取消

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        if (!Preconditions.isEmpty(cancelMap))
            for (INetCancel netCancel : cancelMap.values()) {
                if (Preconditions.isNull(netCancel)) continue;
                netCancel.cancel();
            }
        cancelMap.clear();
        cancelMap = null;
    }

    @Override
    public <T> INetCancel single(NetRequest<T> netRequest) {
        if (Preconditions.isNull(netRequest)) return null;
        INetCancel iNetCancel = mUrlApi.single(netRequest);
        String url = netRequest.getUrl();
        if (!Preconditions.isEmpty(url))
            cancelMap.put(url, iNetCancel);
        return iNetCancel;
    }

    @Override
    public INetCancel multiply(NetRequest... netRequests) {
        INetCancel iNetCancel = mUrlApi.multiply(netRequests);
        String url = multiRequestUrl(netRequests);
        if (!Preconditions.isEmpty(url))
            cancelMap.put(url, iNetCancel);
        return iNetCancel;
    }

    /**
     * 拼接多条网络请求
     *
     * @param netRequests
     * @return
     */
    private String multiRequestUrl(NetRequest... netRequests) {
        if (Preconditions.isNulls(netRequests)) return null;
        StringBuffer buffer = new StringBuffer();
        for (NetRequest request : netRequests) {
            if (Preconditions.isNull(request) || Preconditions.isEmpty(request.getUrl())) continue;
            buffer.append(request.getUrl());
        }
        return buffer.toString();
    }
}
