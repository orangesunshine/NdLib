package com.orange.lib.mvp.view.activity;

import com.orange.lib.mvp.presenter.PullPresenter;
import com.orange.lib.mvp.view.ifc.base.IPullView;

public abstract class PullActivity<P extends PullPresenter> extends NetActivity<P> implements IPullView {

    @Override
    public void enableRefresh(boolean enable) {

    }

    @Override
    public void enableLoadmore(boolean enable) {

    }

    @Override
    public void autoRefresh() {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void finishLoadmore() {

    }
}
