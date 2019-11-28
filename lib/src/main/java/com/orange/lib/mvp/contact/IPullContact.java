package com.orange.lib.mvp.contact;

import com.orange.lib.mvp.presenter.IPullData;
import com.orange.lib.mvp.view.ifc.IPull;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 14:44
 */
public interface IPullContact {
    interface View extends INetContact.View, IPull {
    }

    interface Presenter<V extends IPullContact.View> extends INetContact.Presenter<V>, IPullData {
    }
}
