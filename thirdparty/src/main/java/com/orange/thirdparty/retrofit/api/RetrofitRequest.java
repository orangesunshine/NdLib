package com.orange.thirdparty.retrofit.api;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.request.IRequest;
import com.orange.lib.utils.base.Preconditions;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.retrofit.RetrofitWrapper;
import com.orange.thirdparty.rxjava.RetrofitObserver;

public class RetrofitRequest implements IRequest<RetrofitWrapper> {
    public static RetrofitRequest newInstance() {
        return new RetrofitRequest();
    }

    private RetrofitObserver mObserver;

    private RetrofitRequest() {
        mObserver = RetrofitObserver.newInstance();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 网络请求实现
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public INetCancel request(RetrofitWrapper wrapper) {
        if (Preconditions.isNull(wrapper)) return null;
        return new RetrofitNetCancel(mObserver.subscribe(wrapper.generateObservable(), wrapper.getCallback()));
    }
}
