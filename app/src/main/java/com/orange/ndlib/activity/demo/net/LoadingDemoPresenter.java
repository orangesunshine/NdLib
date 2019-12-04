package com.orange.ndlib.activity.demo.net;

import com.orange.lib.mvp.model.net.callback.loading.LoadingCallback;
import com.orange.lib.mvp.model.net.request.request.NetRequestParams;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.contact.presenter.NetPresenter;

import java.util.HashMap;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:06
 */
public class LoadingDemoPresenter extends NetPresenter<ILoadingDemoContact.View> implements ILoadingDemoContact.Presenter {
    @Override
    public INetCancel getLoadingData() {
        return request(NetRequestParams.NetRequestParamsBuilder.builder().url("http://localhost:8080/ifc/loading1").callback(new LoadingCallback<String>(mView) {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                mView.setLoadingData(data);
            }
        }).build());
    }

    @Override
    public INetCancel getMultiDatas() {
        HashMap<String, String> params = new HashMap<>();
        params.put("params", "Hello Yesterday");
        return serial(NetRequestParams.NetRequestParamsBuilder.builder().url("http://localhost:8080/ifc/loading1").callback(new LoadingCallback<String>(mView) {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                mView.setLoadingData(data);
            }
        }).build(), NetRequestParams.NetRequestParamsBuilder.builder().url("http://localhost:8080/ifc/loading2").params(params).callback(new LoadingCallback<String>(mView) {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                mView.setLoading2Data(data);
            }
        }).build());
    }
}
