package com.orange.lib.mvp.contact.presenter;

import com.orange.lib.common.config.Config;
import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.request.IRequest;
import com.orange.lib.mvp.model.net.request.request.NetRequestParams;
import com.orange.lib.utils.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

import static com.orange.lib.constance.IConst.TYPE_PARALLEL;
import static com.orange.lib.constance.IConst.TYPE_SERIAL;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:54
 */
public class NetPresenter<V extends INetContact.View> extends BasePresenter<V> implements IRequest {
    protected IRequest mRequest = Config.getInstance().getNet();
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
    public <T> INetCancel request(NetRequestParams<T> netRequestParams) {
        if (Preconditions.isNull(netRequestParams)) return null;
        INetCancel iNetCancel = mRequest.request(netRequestParams);
        String url = netRequestParams.getUrl();
        if (!Preconditions.isEmpty(url))
            cancelMap.put(url, iNetCancel);
        return iNetCancel;
    }

    @Override
    public <T extends NetRequestParams<K>, K> INetCancel parallel(T... netRequestParams) {
        INetCancel iNetCancel = mRequest.parallel(netRequestParams);
        String url = multiRequestUrl(TYPE_PARALLEL, netRequestParams);
        if (!Preconditions.isEmpty(url))
            cancelMap.put(url, iNetCancel);
        return iNetCancel;
    }

    @Override
    public INetCancel serial(NetRequestParams... netRequestParams) {
        INetCancel iNetCancel = mRequest.serial(netRequestParams);
        String url = multiRequestUrl(TYPE_SERIAL, netRequestParams);
        if (!Preconditions.isEmpty(url))
            cancelMap.put(url, iNetCancel);
        return iNetCancel;
    }

    /**
     * 拼接多条网络请求
     *
     * @param type             串行、并行
     * @param netRequestParams
     * @return
     */
    private String multiRequestUrl(int type, NetRequestParams... netRequestParams) {
        if (Preconditions.isNulls(netRequestParams)) return null;
        StringBuffer buffer = new StringBuffer();
        buffer.append(TYPE_SERIAL == type ? "serial" : "parallel");
        for (NetRequestParams request : netRequestParams) {
            if (Preconditions.isNull(request) || Preconditions.isEmpty(request.getUrl())) continue;
            buffer.append(request.getUrl());
        }
        return buffer.toString();
    }
}
