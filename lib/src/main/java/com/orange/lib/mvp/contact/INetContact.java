package com.orange.lib.mvp.contact;

import com.orange.lib.loading.api.IApi;
import com.orange.lib.loading.pagestatus.IPage;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.ifc.IMsg;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 14:44
 */
public interface INetContact {
    interface View extends IContact.View, IPage {
    }

    interface Presenter<V extends View> extends IContact.Presenter<V>, IApi {
    }
}
