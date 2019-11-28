package com.orange.lib.mvp.presenter;

import com.orange.lib.mvp.contact.IContact;

public class BasePresenter<V extends IContact.View> implements IContact.Presenter<V> {
    //vars
    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    public void onActivityDestroy() {
        mView = null;
    }
}
