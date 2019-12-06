package com.orange.ndlib.activity.demo.net;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.thirdparty.retrofit.mvp.IRetrofitNetContact;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:08
 */
public interface ILoadingDemoContact {
    interface View extends IRetrofitNetContact.View {
        void setLoadingData(String data);

        void setLoading2Data(String data);
    }

    interface Presenter extends IRetrofitNetContact.Presenter<View> {
        INetCancel getLoadingData();

        INetCancel getMultiDatas();
    }
}
