package com.orange.lib.mvp.presenter;

import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.Ipull;
import com.orange.lib.mvp.view.ifc.base.IView;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:58
 */
public class PullPresenter<V extends IView & ILoading & Ipull> extends NetPresenter<V> implements IPullData {
    public void pullDatas(int pageIndex, Ipull pull) {
        pullDatas(pageIndex, COUNT_ONCE_LOAD, pull);
    }

    @Override
    public void pullDatas(int pageIndex, int count, Ipull pull) {

    }
}
