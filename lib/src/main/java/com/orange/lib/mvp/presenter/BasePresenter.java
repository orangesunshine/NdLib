package com.orange.lib.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.orange.lib.mvp.presenter.ifc.IPresenter;
import com.orange.lib.mvp.view.ifc.base.IView;

public class BasePresenter<V extends IView> implements IPresenter<V> {
    //vars
    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    /**
     * 初始化变量
     */
    public void initVars(Bundle bundle) {
    }

    public void onActivityDestroy() {
    }
}
