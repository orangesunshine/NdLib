package com.orange.ndlib.activity.demo.net;

import com.orange.lib.mvp.contact.INetContact;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:08
 */
public interface INetDemoContact {
    interface View extends INetContact.View {
        void showDialogLoading();

        void setLoadingData(String data);
    }

    interface Presenter extends INetContact.Presenter<View> {
        void pageLoading();

        void pageDialogLoading();

        void pageError();

        void pageContent();
    }
}
