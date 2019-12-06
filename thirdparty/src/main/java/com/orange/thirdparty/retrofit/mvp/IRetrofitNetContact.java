package com.orange.thirdparty.retrofit.mvp;

import com.orange.lib.mvp.contact.INetContact;

public interface IRetrofitNetContact {
    interface View extends INetContact.View {
    }

    interface Presenter<V extends INetContact.View> extends INetContact.Presenter<V> {
    }
}
