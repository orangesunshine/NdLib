package com.orange.ndlib.activity.base.loading;

import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.ifc.IPresenter;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.base.IView;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:08
 */
public interface ILoadingDemoContact {
    interface View extends IView, ILoading {
        void setLoadingData(String data);

        void setLoading2Data(String data);
    }

    interface Presenter extends IPresenter<View>, IUrlApi {
        INetCancel getLoadingData();

        INetCancel getMultiDatas();
    }
}
