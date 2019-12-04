package com.orange.lib.mvp.contact.view;

import com.orange.lib.mvp.contact.IPullContact;
import com.orange.lib.mvp.view.pull.IPull;
import com.orange.lib.mvp.view.pull.SwipeRecyclerViewPull;

public abstract class PullActivity<P extends IPullContact.Presenter> extends NetActivity<P> implements IPull {
    protected IPull mPull;

    @Override
    protected void init() {
        super.init();

        mPull = getPull();
        if (null != mPull) {
            mPull = new SwipeRecyclerViewPull(mHolder);
        }
    }

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

    protected IPull getPull() {
        return null;
    }
}
