package com.orange.ndlib.activity.demo.net;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.LoadingCallback;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.thirdparty.rxjava.RxNetCancel;
import com.orange.thirdparty.rxjava.RxRequest;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.params.SerialRxParams;
import com.orange.thirdparty.rxjava.parse.FlatMapConvert;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:06
 */
public class LoadingDemoPresenter extends com.orange.lib.mvp.contact.presenter.NetPresenter<ILoadingDemoContact.View> implements ILoadingDemoContact.Presenter {
    @Override
    public INetCancel getLoadingData() {
        return new RxNetCancel(RxRequest.newInstance(RxParams.Builder.builder()
                .type(String.class)
                .url("http://localhost:8080/ifc/loading1")
                .build())
                .serial(SerialRxParams.Builder.builder()
                        .url("http://localhost:8080/ifc/loading2")
                        .convert(new FlatMapConvert<String>() {
                            @Override
                            public void convert(BaseResponse<String> response, RxParams params) {
                                params.addParams("count", String.valueOf(response.data.length()));
                            }
                        }).build())
                .subcribe(new LoadingCallback<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        super.onSuccess(data);
                        mView.setLoadingData(data);
                    }
                }));
    }

    @Override
    public INetCancel getMultiDatas() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("params", "Hello Yesterday");
//        return serial(Params.Builder.builder().url("http://localhost:8080/ifc/loading1").callback(new LoadingCallback<String>(mView) {
//            @Override
//            public void onSuccess(String data) {
//                super.onSuccess(data);
//                mView.setLoadingData(data);
//            }
//        }).build(), Params.Builder.builder().url("http://localhost:8080/ifc/loading2").params(params).callback(new LoadingCallback<String>(mView) {
//            @Override
//            public void onSuccess(String data) {
//                super.onSuccess(data);
//                mView.setLoading2Data(data);
//            }
//        }).build());
        return null;
    }
}
