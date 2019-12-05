package com.orange.thirdparty.retrofit.api;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.request.IRequest;
import com.orange.lib.utils.base.Preconditions;
import com.orange.thirdparty.retrofit.RetrofitNetCancel;
import com.orange.thirdparty.retrofit.RetrofitWrapper;
import com.orange.thirdparty.rxjava.NetObserver;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class RetrofitRequest implements IRequest<RetrofitWrapper> {
    public static RetrofitRequest netInstance() {
        return new RetrofitRequest();
    }

    private NetObserver mObserver;

    private RetrofitRequest() {
        mObserver = NetObserver.newInstance();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 网络请求实现
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public INetCancel single(RetrofitWrapper wrapper) {
        if (Preconditions.isNull(wrapper)) return null;
        Observable<ResponseBody> observable = wrapper.observable();
        return new RetrofitNetCancel(mObserver.subResponseBody(observable, wrapper.getCallback()));
    }

    @Override
    public INetCancel parallel(RetrofitWrapper wrapper) {
        if (Preconditions.isNull(wrapper)) return null;
        Observable<ResponseBody> observable = wrapper.observable();
//        return new RetrofitNetCancel(mObserver.parallel(observable, wrapper.getCallback()));
        return null;
    }

    @Override
    public INetCancel serial(RetrofitWrapper wrapper) {
        if (Preconditions.isNull(wrapper)) return null;
        return new RetrofitNetCancel(mObserver.subResponseBody(wrapper.serialObservable(), wrapper.getCallback()));
    }

    ///////////////////////////////////////////////////////////////////////////
    // 工具方法
    ///////////////////////////////////////////////////////////////////////////

    private INetCancel cancelNetList(List<INetCancel> netCancels) {
        if (Preconditions.isEmpty(netCancels)) return null;
        return new INetCancel() {
            @Override
            public void cancel() {
                for (INetCancel netCancel : netCancels) {
                    if (Preconditions.isNull(netCancel)) continue;
                    netCancel.cancel();
                }
            }
        };
    }
}
