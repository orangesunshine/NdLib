package com.orange.lib.mvp.contact.presenter;

import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.contact.IPullContact;
import com.orange.lib.mvp.model.net.request.IPullData;
import com.orange.lib.mvp.model.net.request.request.PullParams;
import com.orange.lib.mvp.model.net.request.request.Wrapper;
import com.orange.lib.mvp.view.pull.IPull;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:58
 */
public class PullPresenter<V extends IPullContact.View, N extends Wrapper> extends NetPresenter<V, N> implements IPullData {
    public void pullDatas(int pageIndex, IPull pull) {
        pullDatas(pageIndex, IConst.PULL_ITEM_COUNT, pull);
    }

    @Override
    public void pullDatas(int pageIndex, int count, IPull pull) {

    }

    @Override
    public void OnRefresh(PullParams pullnetRequest) {

    }

    @Override
    public void onLoadmore() {

    }
}
