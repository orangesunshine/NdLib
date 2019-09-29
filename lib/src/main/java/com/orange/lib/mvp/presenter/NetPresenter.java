package com.orange.lib.mvp.presenter;

import com.orange.lib.common.config.Config;
import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.ifc.INetPresenter;
import com.orange.lib.mvp.view.ifc.base.INetView;
import com.orange.lib.utils.base.Preconditions;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:54
 */
public class NetPresenter<V extends INetView> extends BasePresenter<V> implements INetPresenter<V> {
    protected IUrlApi mUrlApi = Config.getInstance().getNet();
    protected List<INetCancel> mNetCancels = new LinkedList<>();

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        if (!Preconditions.isEmpty(mNetCancels))
            for (INetCancel netCancel : mNetCancels) {
                if (Preconditions.isNull(netCancel)) continue;
                netCancel.cancel();
            }
    }

    @Override
    public <T> INetCancel singleRequest(NetRequest<T> netRequest) {
        INetCancel iNetCancel = mUrlApi.singleRequest(netRequest);
        mNetCancels.add(iNetCancel);
        return iNetCancel;
    }

    @Override
    public INetCancel request(NetRequest... netRequests) {
        INetCancel iNetCancel = mUrlApi.request(netRequests);
        mNetCancels.add(iNetCancel);
        return iNetCancel;
    }
}
