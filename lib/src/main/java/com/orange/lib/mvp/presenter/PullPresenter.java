package com.orange.lib.mvp.presenter;

import com.orange.lib.constance.IConst;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.loading.request.PullnetRequest;
import com.orange.lib.mvp.contact.IPullContact;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.view.ifc.IPull;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:58
 */
public class PullPresenter<V extends IPullContact.View> extends NetPresenter<V> implements IPullData {
    public void pullDatas(int pageIndex, IPull pull) {
        pullDatas(pageIndex, IConst.PULL_ITEM_COUNT, pull);
    }

    @Override
    public void pullDatas(int pageIndex, int count, IPull pull) {

    }

    @Override
    public <T> void OnRefresh(PullnetRequest<T> pullnetRequest) {

    }

    @Override
    public void onLoadmore() {

    }

    @Override
    public <T> INetCancel single(NetRequest<T> netRequest) {
        return super.single(netRequest);
    }

    @Override
    public INetCancel multiply(NetRequest... netRequests) {
        return super.multiply(netRequests);
    }
}
