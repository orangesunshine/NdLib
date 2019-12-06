package com.orange.thirdparty.retrofit.mvp;

import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.contact.presenter.NetPresenter;
import com.orange.thirdparty.retrofit.RetrofitWrapper;

public class RetrofitNetPresenter<V extends INetContact.View> extends NetPresenter<V, RetrofitWrapper> {
}
