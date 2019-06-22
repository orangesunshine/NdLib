package com.orange.lib.mvp.view.activity;

import android.os.Bundle;

import com.orange.lib.mvp.presenter.BasePresenter;
import com.orange.lib.mvp.presenter.IBuildPresenter;
import com.orange.lib.mvp.view.IView;
import com.orange.lib.mvp.view.activity.base.BaseActivity;
import com.orange.lib.utils.ReflectionUtils;

public abstract class PresenterActivity<P extends BasePresenter> extends BaseActivity implements IBuildPresenter<P>, IView {
    // <editor-fold defaultstate="collapsed" desc="presenter">
    //vars
    protected P mPresenter;

    /**
     * 初始化（控件相关）
     */
    @Override
    public void init() {
        super.init();
        if (null != mPresenter)
            mPresenter.attachView(this);
    }

    /**
     * 初始化
     *
     * @param bundle
     */
    @Override
    public void initVars(Bundle bundle) {
        super.initVars(bundle);
        mPresenter = generatePresenter();
    }

    @Override
    public P generatePresenter() {
        return ReflectionUtils.getGenericActualTypeArgInstance(this);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="吐司方法">
//    @Override
//    public void showToast(CharSequence text) {
//        if (null != mToast)
//            mToast.showToast(text);
//    }
//
//    @Override
//    public void showToast(int stringId) {
//        if (null != mToast)
//            mToast.showToast(stringId);
//    }
    // </editor-fold>
}
