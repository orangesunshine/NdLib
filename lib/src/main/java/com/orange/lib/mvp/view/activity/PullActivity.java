package com.orange.lib.mvp.view.activity;

import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.mvp.presenter.IPullData;
import com.orange.lib.mvp.presenter.ifc.IPresenter;
import com.orange.lib.mvp.view.ifc.Ipull;

public abstract class PullActivity<P extends IPresenter & IUrlApi & IPullData> extends NetActivity<P> implements Ipull {
    protected Ipull mPull;

    @Override
    public void enableRefresh(boolean enable) {
        if (null != mPull)
            mPull.enableRefresh(enable);
    }

    @Override
    public void enableLoadmore(boolean enable) {
        if (null != mPull)
            mPull.enableLoadmore(enable);
    }

    @Override
    public void autoRefresh() {
        if (null != mPull)
            mPull.autoRefresh();
    }

    @Override
    public void finishRefresh() {
        if (null != mPull)
            mPull.finishRefresh();
    }

    @Override
    public void finishLoadmore() {
        if (null != mPull)
            mPull.finishLoadmore();
    }

    protected Ipull getPull() {
        return null;
    }
}
