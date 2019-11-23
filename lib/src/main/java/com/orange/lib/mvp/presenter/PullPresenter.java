package com.orange.lib.mvp.presenter;

import com.orange.lib.constance.IConst;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.Ipull;
import com.orange.lib.mvp.view.ifc.base.IView;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:58
 */
public class PullPresenter<V extends IView & ILoading & Ipull> extends NetPresenter<V> implements IPullData {
    public void pullDatas(int pageIndex, Ipull pull) {
        pullDatas(pageIndex, IConst.PULL_ITEM_COUNT, pull);
    }

    @Override
    public void pullDatas(int pageIndex, int count, Ipull pull) {

    }

    @Override
    public <T> INetCancel singleRequest(NetRequest<T> netRequest) {
        return super.singleRequest(netRequest);
    }

    @Override
    public INetCancel request(NetRequest... netRequests) {
        return super.request(netRequests);
    }
}
