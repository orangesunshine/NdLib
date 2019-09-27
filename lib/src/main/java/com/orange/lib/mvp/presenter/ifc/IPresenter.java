package com.orange.lib.mvp.presenter.ifc;


import com.orange.lib.mvp.view.ifc.base.IView;

public interface IPresenter<V extends IView> {
    void attachView(V view);
}
