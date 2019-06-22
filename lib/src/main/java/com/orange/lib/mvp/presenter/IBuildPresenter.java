package com.orange.lib.mvp.presenter;


import com.orange.lib.mvp.presenter.ifc.IPresenter;

public interface IBuildPresenter<P extends IPresenter> {
    P generatePresenter();
}
