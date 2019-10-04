package com.orange.ndlib.activity.base.loading.page;

import com.orange.lib.loading.pagestatus.IPageStatus;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.base.IView;

public interface IPageDemoContact {
    interface View extends IPageStatus, IView, ILoading {
        void setLoadingData(String data);
    }

    interface Presenter extends com.orange.lib.mvp.presenter.ifc.IPresenter<View>, com.orange.lib.loading.api.IUrlApi {
        INetCancel getLoadingData();
    }
}
