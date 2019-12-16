package com.orange.ndlib.activity.demo.net.parallel;

import com.orange.lib.mvp.contact.presenter.NetPresenter;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;

public class ParallelNetDemoPresenter extends NetPresenter<IParallelNetDemoContact.View> implements IParallelNetDemoContact.Presenter {
    @Override
    public INetCancel getSerialData() {
        return null;
    }
}
