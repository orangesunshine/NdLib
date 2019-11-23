package com.orange.lib.mvp.presenter;

import com.orange.lib.common.config.Config;
import com.orange.lib.constance.IConst;
import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.view.ifc.Ipull;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 12:11
 */
public class SwipeRecyclerViewPullData implements IPullData {
    protected IUrlApi mUrlApi = Config.getInstance().getNet();
    protected List<INetCancel> mNetCancels = new LinkedList<>();
    private int mPageIndex;

    public void pullDatas(int pageIndex, Ipull pull) {
        pullDatas(pageIndex, IConst.PULL_ITEM_COUNT, pull);
    }

    @Override
    public void pullDatas(int pageIndex, int count, Ipull pull) {
        mPageIndex = pageIndex;
    }
}
