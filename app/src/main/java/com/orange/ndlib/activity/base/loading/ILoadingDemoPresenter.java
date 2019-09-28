package com.orange.ndlib.activity.base.loading;

import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.ifc.INetPresenter;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:09
 */
public interface ILoadingDemoPresenter extends INetPresenter<ILoadingDemoView> {
    INetCancel getLoadingData();//获取loading数据

    INetCancel getPullData();

    INetCancel getDatas();
}
