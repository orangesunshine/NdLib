package com.orange.ndlib.activity.base.loading.page;

import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.ifc.INetPresenter;
import com.orange.lib.mvp.view.ifc.base.IPageNetView;

public interface IPageDemoContact {
    interface View extends IPageNetView {
        void setLoadingData(String data);
    }

    interface Presenter extends INetPresenter<View> {
        INetCancel getLoadingData();
    }
}
