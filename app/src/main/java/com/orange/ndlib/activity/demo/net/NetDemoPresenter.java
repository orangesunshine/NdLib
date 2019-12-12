package com.orange.ndlib.activity.demo.net;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.contact.presenter.NetPresenter;
import com.orange.lib.mvp.model.net.callback.loading.PageCallback;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.thirdparty.rxjava.RxRequest;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.params.RxSerialParams;
import com.orange.thirdparty.rxjava.params.generate.IGenerateObservable;
import com.orange.thirdparty.rxjava.parse.RxMapper;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:06
 */
public class NetDemoPresenter extends NetPresenter<INetDemoContact.View> implements INetDemoContact.Presenter {
    @Override
    public INetCancel getLoadingData() {
        return RxRequest.newInstance(
                RxParams.Builder.builder()
                        .url("http://localhost:8080/ifc/loading1")
                        .build())
                .serial(RxSerialParams.Builder.builder()
                        .url("http://localhost:8080/ifc/loading2")
                        .flatMapConvert(new RxMapper<String>(String.class) {

                            @Override
                            public void convert(BaseResponse<String> t, IGenerateObservable generate) {
                            }
                        })
                        .build())
                .subscribeResponseBody(new PageCallback<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        super.onSuccess(data);
                        mView.setLoadingData(data);
                    }
                });
    }

    @Override
    public INetCancel getMultiDatas() {
        return null;
//        return RxRequest.newInstance(RxParams.Builder.builder()
//                .url("http://localhost:8080/ifc/loading1")
//                .build()
//                .parallel(RxParams.Builder.builder()
//                        .url("http://localhost:8080/ifc/loading2")
//                        .build(String.class), new BiFunction<String, String, String>() {
//                    @Override
//                    public String apply(String t1, String t2) throws Exception {
//                        return stringBaseResponse.data + stringBaseResponse2.data;
//                    }
//                })
//                .subcribe(new PageCallback<String>(mView) {
//                    @Override
//                    public void onSuccess(String data) {
//                        super.onSuccess(data);
//                        mView.setLoadingData(data);
//                    }
//                });
    }

    @Override
    public void pageLoading() {
        mView.showLoading();
    }

    @Override
    public void pageDialogLoading() {
        mView.showDialogLoading();
    }

    @Override
    public void pageError() {
        mView.showError();
    }

    @Override
    public void pageContent() {
        mView.showContent();
    }
}
