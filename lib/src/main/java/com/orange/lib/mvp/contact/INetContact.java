package com.orange.lib.mvp.contact;

import com.orange.lib.mvp.model.net.request.IRequest;
import com.orange.lib.mvp.view.page.loading.IPage;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 14:44
 */
public interface INetContact {
    interface View extends IContact.View, IPage {
    }

    interface Presenter<V extends View> extends IContact.Presenter<V>, IRequest {
    }
}
