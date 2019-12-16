package com.orange.ndlib.activity.demo.net.parallel;

import com.orange.lib.mvp.contact.presenter.NetPresenter;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.thirdparty.retrofit.request.RetrofitRequest;
import com.orange.thirdparty.retrofit.responsebody.params.RbParams;

public class ParallelNetDemoPresenter extends NetPresenter<IParallelNetDemoContact.View> implements IParallelNetDemoContact.Presenter {
    @Override
    public INetCancel getAllRbData() {
        return RetrofitRequest.newInstance(RbParams.Builder.builder().url("http://localhost:8080/parallel/first").build()).parallel();
    }

    @Override
    public INetCancel getAllTData() {
        return null;
    }

    @Override
    public INetCancel getTParallelRbData() {
        return null;
    }

    @Override
    public INetCancel getRbParallelTData() {
        return null;
    }
}
