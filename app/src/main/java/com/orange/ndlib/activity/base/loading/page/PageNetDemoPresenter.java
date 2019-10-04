package com.orange.ndlib.activity.base.loading.page;

import com.orange.lib.loading.callback.PageStatusNetCallback;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.NetPresenter;

import java.util.HashMap;

public class PageNetDemoPresenter extends NetPresenter<IPageDemoContact.View> implements IPageDemoContact.Presenter {
    @Override
    public INetCancel getLoadingData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("params", "Hello manila");
        return mUrlApi.singleRequest(NetRequest.NetBuilder.builder().url("http://localhost:8080/ifc/loading2").params(params).callback(new PageStatusNetCallback<String>(mView) {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                if (null != mView) {
                    mView.setLoadingData(s);
                }
            }
        }).build());
    }
}
