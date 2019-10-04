package com.orange.lib.mvp.presenter;

import com.orange.lib.common.config.Config;
import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.base.IView;
import com.orange.lib.utils.base.Preconditions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:54
 */
public class NetPresenter<V extends IView & ILoading> extends BasePresenter<V> implements IUrlApi {
    protected IUrlApi mUrlApi = Config.getInstance().getNet();
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
    public <T> INetCancel singleRequest(NetRequest<T> netRequest) {
        if (Preconditions.isNull(netRequest)) return null;
        INetCancel iNetCancel = mUrlApi.singleRequest(netRequest);
        String url = netRequest.getUrl();
        if (!Preconditions.isEmpty(url))
            cancelMap.put(url, iNetCancel);
        return iNetCancel;
    }

    @Override
    public INetCancel request(NetRequest... netRequests) {
        INetCancel iNetCancel = mUrlApi.request(netRequests);
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
