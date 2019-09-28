package com.orange.lib.mvp.presenter.ifc;


import android.os.Bundle;

import com.orange.lib.mvp.view.ifc.base.IView;

public interface IPresenter<V extends IView> {
    /**
     * 关联视图
     *
     * @param view
     */
    void attachView(V view);

    /**
     * 初始化变量
     */
    void initVars(Bundle bundle);

    /**
     * activity销毁回调
     */
    void onActivityDestroy();
}
