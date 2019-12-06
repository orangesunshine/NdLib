package com.orange.thirdparty.retrofit.mvp;

import com.orange.lib.mvp.contact.INetContact;
import com.orange.thirdparty.retrofit.RetrofitWrapper;

public interface IRetrofitNetContact {
    interface View extends INetContact.View {
    }

    interface Presenter<V extends INetContact.View> extends INetContact.Presenter<V, RetrofitWrapper> {
    }
}
