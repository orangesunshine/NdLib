package com.orange.ndlib.activity.demo.loading;

import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:08
 */
public interface ILoadingDemoContact {
    interface View extends INetContact.View {
        void setLoadingData(String data);

        void setLoading2Data(String data);
    }

    interface Presenter extends INetContact.Presenter<View> {
        INetCancel getLoadingData();

        INetCancel getMultiDatas();
    }
}
