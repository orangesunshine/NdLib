package com.orange.ndlib.activity.demo.net;

import com.orange.lib.mvp.contact.presenter.NetPresenter;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:06
 */
public class NetDemoPresenter extends NetPresenter<INetDemoContact.View> implements INetDemoContact.Presenter {

    @Override
    public void pageLoading() {
        mView.showLoading();
    }

    @Override
    public void pageDialogLoading() {
        mView.showDialogLoading();
    }

    @Override
    public void pageError() {
        mView.showError();
    }

    @Override
    public void pageContent() {
        mView.showContent();
    }
}
