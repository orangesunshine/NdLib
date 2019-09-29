package com.orange.ndlib.activity.base.loading;

import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.ifc.INetPresenter;
import com.orange.lib.mvp.view.ifc.base.INetView;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:08
 */
public interface ILoadingDemoContact{
    interface View extends INetView {
        void setLoadingData(String data);
    }

    interface Presenter extends INetPresenter<View> {
        INetCancel getLoadingData();
    }
}
