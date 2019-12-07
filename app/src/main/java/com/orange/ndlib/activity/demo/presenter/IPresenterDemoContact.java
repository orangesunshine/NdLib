package com.orange.ndlib.activity.demo.presenter;

import com.orange.lib.mvp.contact.IContact;

public interface IPresenterDemoContact {
    interface View extends IContact.View {
        void showPresenterMsg(String msg);
    }

    interface Presenter extends IContact.Presenter<View> {
        void presenterTest();
    }
}
