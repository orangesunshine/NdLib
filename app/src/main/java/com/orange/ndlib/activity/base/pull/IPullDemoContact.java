package com.orange.ndlib.activity.base.pull;

import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.IPullData;
import com.orange.lib.mvp.presenter.ifc.IPresenter;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.Ipull;
import com.orange.lib.mvp.view.ifc.base.IView;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 9:13
 */
public interface IPullDemoContact {
    interface View extends IView, ILoading, Ipull {
        void setPullDatas();
    }

    interface Presenter extends IPresenter<View>, IUrlApi, IPullData {
        INetCancel getPullDatas(boolean init);
    }
}
