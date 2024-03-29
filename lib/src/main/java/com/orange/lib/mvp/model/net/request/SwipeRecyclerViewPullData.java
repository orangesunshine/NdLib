package com.orange.lib.mvp.model.net.request;

import com.orange.lib.common.config.Config;
import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.request.request.PullParams;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.view.pull.IPull;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 12:11
 */
public class SwipeRecyclerViewPullData implements IPullData {
    protected IRequest mUrlApi = Config.getInstance().getNet();
    protected List<INetCancel> mNetCancels = new LinkedList<>();
    private int mPageIndex;

    public void pullDatas(int pageIndex, IPull pull) {
        pullDatas(pageIndex, IConst.PAGE_SIZE, pull);
    }

    @Override
    public void pullDatas(int pageIndex, int count, IPull pull) {
        mPageIndex = pageIndex;
    }

    @Override
    public void OnRefresh(PullParams pullnetRequest) {

    }

    @Override
    public void onLoadmore() {

    }
}
