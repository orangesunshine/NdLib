package com.orange.ndlib.activity.demo.net.parallel;

import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;

public interface IParallelNetDemoContact {
    interface View extends INetContact.View {
        void showParallelResult(String result);
    }

    interface Presenter extends INetContact.Presenter<View> {
        INetCancel getAllRbData();

        INetCancel getAllTData();

        INetCancel getTParallelRbData();

        INetCancel getRbParallelTData();
    }
}
