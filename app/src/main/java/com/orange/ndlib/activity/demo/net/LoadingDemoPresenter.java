package com.orange.ndlib.activity.demo.net;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.contact.presenter.NetPresenter;
import com.orange.lib.mvp.model.net.callback.loading.LoadingCallback;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.request.request.Params;
import com.orange.thirdparty.retrofit.RetrofitParams;
import com.orange.thirdparty.retrofit.RetrofitWrapper;
import com.orange.thirdparty.rxjava.parse.FlatMapConvert;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:06
 */
public class LoadingDemoPresenter extends NetPresenter<ILoadingDemoContact.View> implements ILoadingDemoContact.Presenter {
    @Override
    public INetCancel getLoadingData() {
        return single(RetrofitWrapper.Builder.builder()
                .params(RetrofitParams.Builder.builder().url("http://localhost:8080/ifc/loading1").build())
                .params(RetrofitParams.Builder.builder().convert(new FlatMapConvert<String>() {
                    @Override
                    public void convert(BaseResponse<String> response, Params params) {
                        params.addParams("params", response.data);
                    }
                }).url("http://localhost:8080/ifc/loading2").build())
                .callback(new LoadingCallback<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        super.onSuccess(data);
                        mView.setLoadingData(data);
                    }
                }).build());
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
