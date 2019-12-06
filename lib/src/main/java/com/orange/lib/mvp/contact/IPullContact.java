package com.orange.lib.mvp.contact;

import com.orange.lib.mvp.model.net.request.IPullData;
import com.orange.lib.mvp.model.net.request.request.Wrapper;
import com.orange.lib.mvp.view.pull.IPull;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 14:44
 */
public interface IPullContact {
    interface View extends INetContact.View, IPull {
    }

    interface Presenter<V extends IPullContact.View, N extends Wrapper> extends INetContact.Presenter<V, N>, IPullData {
    }
}
