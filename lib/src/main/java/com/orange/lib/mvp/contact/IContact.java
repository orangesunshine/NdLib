package com.orange.lib.mvp.contact;

import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.IMsg;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 14:44
 */
public interface IContact {
    interface View extends IMsg, ILoading {
    }

    interface Presenter<V extends View> {
        /**
         * 关联视图
         *
         * @param view
         */
        void attachView(V view);

        /**
         * activity销毁回调
         */
        void onActivityDestroy();
    }
}
