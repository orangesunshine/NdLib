package com.orange.lib.mvp.view.activity;

import android.os.Bundle;

import com.orange.lib.component.pull.callback.DefaultPullCallback;
import com.orange.lib.mvp.presenter.BasePresenter;


public abstract class PullNetActivity<P extends BasePresenter> extends PresenterActivity<P> {
    // <editor-fold defaultstate="collapsed" desc="headerNdfooter">
    protected DefaultPullCallback mPull;

    /**
     * 初始化
     *
     * @param bundle
     */
    @Override
    public void initVars(Bundle bundle) {
        super.initVars(bundle);
        buildHeaderNdFooter();
    }

    public abstract void buildHeaderNdFooter();
    // </editor-fold>
}
