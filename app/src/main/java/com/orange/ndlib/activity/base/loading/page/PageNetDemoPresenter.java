package com.orange.ndlib.activity.base.loading.page;

import com.orange.lib.loading.callback.PageStatusNetCallback;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.NetPresenter;

public class PageNetDemoPresenter extends NetPresenter<IPageDemoContact.View> implements IPageDemoContact.Presenter {
    @Override
    public INetCancel getLoadingData() {
        return mUrlApi.singleRequest(NetRequest.NetBuilder.builder().url("http://localhost:8080/ifc/loading1").callback(new PageStatusNetCallback<String>(mView)).build());
    }
}
