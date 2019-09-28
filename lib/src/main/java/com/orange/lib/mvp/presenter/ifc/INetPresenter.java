package com.orange.lib.mvp.presenter.ifc;

import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.mvp.view.ifc.base.INetView;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:50
 */
public interface INetPresenter<V extends INetView> extends IPresenter<V>, IUrlApi {
}
