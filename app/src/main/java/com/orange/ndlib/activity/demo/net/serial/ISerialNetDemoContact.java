package com.orange.ndlib.activity.demo.net.serial;

import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;

public interface ISerialNetDemoContact {
    interface View extends INetContact.View {
        void showSerialResult(String result);
    }

    interface Presenter extends INetContact.Presenter<View> {
        INetCancel getAllRbData();

        INetCancel getAllTData();

        INetCancel getTSerialRbData();

        INetCancel getRbSerialTData();
    }
}
