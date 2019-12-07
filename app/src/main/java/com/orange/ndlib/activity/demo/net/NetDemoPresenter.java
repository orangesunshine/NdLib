package com.orange.ndlib.activity.demo.net;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.contact.presenter.NetPresenter;
import com.orange.lib.mvp.model.net.callback.loading.PageCallback;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.thirdparty.rxjava.RxRequest;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.parse.FlatMapConvert;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:06
 */
public class NetDemoPresenter extends NetPresenter<INetDemoContact.View> implements INetDemoContact.Presenter {
    @Override
    public INetCancel getLoadingData() {
        return RxRequest.newInstance(RxParams.Builder.builder()
                .url("http://localhost:8080/ifc/loading1")
                .build(String.class))
                .serialResponseBody(RxParams.Builder.builder().url("http://localhost:8080/ifc/loading2").build(),
                        new FlatMapConvert<BaseResponse<String>>() {
                            @Override
                            public void convert(BaseResponse<String> response, RxParams params) {
                                params.addParams("params", response.data);
                            }
                        })
                .subcribe(new PageCallback<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        super.onSuccess(data);
                        mView.setLoadingData(data);
                    }
                });
    }

    @Override
    public INetCancel getMultiDatas() {
        return RxRequest.newInstance(RxParams.Builder.builder()
                .url("http://localhost:8080/ifc/loading1")
                .build(String.class))
                .parallel(RxParams.Builder.builder()
                        .url("http://localhost:8080/ifc/loading2")
                        .build(String.class), new BiFunction<BaseResponse<String>, BaseResponse<String>, String>() {
                    @Override
                    public String apply(BaseResponse<String> stringBaseResponse, BaseResponse<String> stringBaseResponse2) throws Exception {
                        return stringBaseResponse.data + stringBaseResponse2.data;
                    }
                })
                .serial(RxParams.Builder.builder()
                        .url("http://localhost:8080/ifc/loading3")
                        .build(String.class), new FlatMapConvert<String>() {
                    @Override
                    public void convert(String response, RxParams params) {
                        params.addParams("params1", response);
                        AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                            @Override
                            public void run() {
                                mView.msg(response);
                            }
                        });
                    }
                })
                .subcribe(new PageCallback<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        super.onSuccess(data);
                        mView.setLoadingData(data);
                    }
                });
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
