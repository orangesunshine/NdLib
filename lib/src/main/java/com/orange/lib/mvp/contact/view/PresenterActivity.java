package com.orange.lib.mvp.contact.view;

import android.os.Bundle;

import com.orange.lib.mvp.contact.IContact;
import com.orange.lib.mvp.view.loading.ILoading;
import com.orange.lib.mvp.view.loading.Loading;
import com.orange.lib.utils.base.Preconditions;
import com.orange.lib.utils.toast.Toasts;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 17:19
 */
public abstract class PresenterActivity<P extends IContact.Presenter> extends BaseActivity implements IContact.View {
    //vars
    protected P mPresenter;
    //views
    protected ILoading mLoading;//loading

    @Override
    protected void initVars(Bundle bundle) {
        super.initVars(bundle);
        mLoading = getLoading();
        if (null == mLoading)
            mLoading = new Loading(mActivity);
        //presenter关联视图
        mPresenter = getPresenter();
        if (!Preconditions.isNull(mPresenter)) {
            mPresenter.attachView(this);
        }
    }

    protected abstract P getPresenter();

    @Override
    public void showMsg(CharSequence charSequence) {
        Toasts.showMsg(charSequence);
    }

    @Override
    public void showMsg(int stringId) {
        Toasts.showMsg(getResources().getText(stringId));
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (Preconditions.isNull(mLoading))
            mLoading = new Loading(mActivity);
        if (isActivityAlive())
            mLoading.showLoading();
    }

    /**
     * 隐藏
     */
    @Override
    public void dismissLoading() {
        if (isActivityAlive() && !Preconditions.isNull(mLoading))
            mLoading.dismissLoading();
    }

    /**
     * 自定义实现loading
     *
     * @return
     */
    protected ILoading getLoading() {
        return null;
    }

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        if (!Preconditions.isNull(mLoading))
            mLoading.dismissLoading();
        if (!Preconditions.isNull(mPresenter))
            mPresenter.onActivityDestroy();
    }
}
