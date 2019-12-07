package com.orange.ndlib.activity.demo.presenter;

import com.orange.lib.mvp.contact.presenter.BasePresenter;

public class PresenterDemoPresenter extends BasePresenter<IPresenterDemoContact.View> implements IPresenterDemoContact.Presenter {
    @Override
    public void presenterTest() {
        mView.showPresenterMsg("presenterTest");
    }
}
