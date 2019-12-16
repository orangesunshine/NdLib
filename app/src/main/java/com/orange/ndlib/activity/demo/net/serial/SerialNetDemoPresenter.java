package com.orange.ndlib.activity.demo.net.serial;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.contact.presenter.NetPresenter;
import com.orange.lib.mvp.model.net.callback.loading.LoadingCallback;
import com.orange.lib.mvp.model.net.callback.loading.PageCallback;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.ndlib.activity.demo.retrofit.RetrofitDemoApi;
import com.orange.thirdparty.retrofit.client.RetrofitClient;
import com.orange.thirdparty.retrofit.request.RetrofitRequest;
import com.orange.thirdparty.retrofit.responsebody.mapper.RbMapper;
import com.orange.thirdparty.retrofit.mapper.TMapper;
import com.orange.thirdparty.retrofit.responsebody.params.RbParams;

import java.util.List;

import io.reactivex.Observable;

public class SerialNetDemoPresenter extends NetPresenter<ISerialNetDemoContact.View> implements ISerialNetDemoContact.Presenter {

    @Override
    public INetCancel getAllRbData() {
        return RetrofitRequest.newInstance(
                RbParams.Builder.builder()
                        .url("http://localhost:8080/serial/first")
                        .build())
                .serial(new RbMapper<String>() {
                    @Override
                    public Observable convert(BaseResponse<String> t) {
                        return RbParams.Builder.builder().url("http://localhost:8080/serial/third").build().getObservable();
                    }
                })
                .subscribeResponseBody(new PageCallback<List<String>>(mView) {
                    @Override
                    public void onSuccess(List<String> data) {
                        super.onSuccess(data);
                        mView.showSerialResult("Method-getAllRbData: " + data);
                    }
                });
    }

    @Override
    public INetCancel getAllTData() {
        return RetrofitRequest.newInstance(RetrofitClient.getRetrofitInstance().create(RetrofitDemoApi.class).getRetrofitFirstDemo("first", "first"))
                .serial(new TMapper<String>() {
                    @Override
                    public Observable apply(BaseResponse<String> response) throws Exception {
                        return RetrofitClient.getRetrofitInstance().create(RetrofitDemoApi.class).getRetrofitFirstDemo("second", response.data);
                    }
                }).subscribe(new LoadingCallback<String>(mView) {
                    @Override
                    public void onSuccess(String ret) {
                        if (null != mView)
                            mView.showSerialResult("Method-getAllTData: " + ret);
                    }
                });
    }

    @Override
    public INetCancel getTSerialRbData() {
        return RetrofitRequest.newInstance(RetrofitClient.getRetrofitInstance().create(RetrofitDemoApi.class).getRetrofitFirstDemo("first", "first"))
                .serial(new TMapper<String>() {
                    @Override
                    public Observable apply(BaseResponse<String> response) throws Exception {
                        return RbParams.Builder.builder()
                                .url("http://localhost:8080/serial/second")
                                .addParams("params", response.data)
                                .build().getObservable();
                    }
                })
                .subscribeResponseBody(new LoadingCallback<String>(mView) {
                    @Override
                    public void onSuccess(String ret) {
                        if (null != mView)
                            mView.showSerialResult("Method-getTSerialRbData: " + ret);
                    }
                });
    }

    @Override
    public INetCancel getRbSerialTData() {
        return RetrofitRequest.newInstance(
                RbParams.Builder.builder()
                        .url("http://localhost:8080/serial/first")
                        .build())
                .serial(new RbMapper<String>() {
                    @Override
                    public Observable convert(BaseResponse<String> t) {
                        return RetrofitClient.getRetrofitInstance().create(RetrofitDemoApi.class).getRetrofitFirstDemo("first", "first" + t.data);
                    }
                })
                .subscribe(new PageCallback<String>(mView) {
                    @Override
                    public void onSuccess(String list) {
                        super.onSuccess(list);
                        mView.showSerialResult("Method-getRbSerialTData: " + list);
                    }
                });
    }
}
