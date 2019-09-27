package com.orange.lib.mvp.presenter;

import com.orange.lib.mvp.presenter.ifc.INetPresenter;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.base.INetView;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:54
 */
public class NetPresenter<V extends INetView> extends BasePresenter<V> implements INetPresenter {
    @Override
    public void getNetDatas(String url, ILoading loading) {

    }
}
