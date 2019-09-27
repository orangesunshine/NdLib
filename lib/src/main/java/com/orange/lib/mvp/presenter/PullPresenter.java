package com.orange.lib.mvp.presenter;

import com.orange.lib.mvp.presenter.ifc.IPullPresenter;
import com.orange.lib.mvp.view.ifc.Ipull;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:58
 */
public class PullPresenter extends NetPresenter implements IPullPresenter {
    @Override
    public void pullDatas(String url, int pageIndex, Ipull pull) {
        pullDatas(url, pageIndex, COUNT_ONCE_LOAD, pull);
    }

    @Override
    public void pullDatas(String url, int pageIndex, int count, Ipull pull) {

    }
}
