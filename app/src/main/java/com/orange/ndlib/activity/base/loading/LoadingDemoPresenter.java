package com.orange.ndlib.activity.base.loading;

import com.orange.lib.loading.callback.LoadingNetCallback;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.NetPresenter;

import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:06
 */
public class LoadingDemoPresenter extends NetPresenter<ILoadingDemoView> implements ILoadingDemoPresenter {
    @Override
    public INetCancel getLoadingData() {
        return singleRequest(NetRequest.NetBuilder.builder().url("http://localhost:8080/ifc/loading").callback(new LoadingNetCallback<String>(mView) {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                mView.setLoadingDatas(data);
            }
        }).build());
    }

    @Override
    public INetCancel getPullData() {
        return singleRequest(NetRequest.NetBuilder.builder().url("http://localhost:8080/ifc/pull").callback(new LoadingNetCallback<List<String>>(mView) {
            @Override
            public void onSuccess(List<String> data) {
                super.onSuccess(data);
                mView.setPullDatas(data);
            }
        }).build());
    }

    @Override
    public INetCancel getDatas() {
        return request(NetRequest.NetBuilder.builder().url("http://localhost:8080/ifc/loading").callback(new LoadingNetCallback<String>(mView) {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                mView.setLoadingDatas(data);
            }
        }).build(), NetRequest.NetBuilder.builder().url("http://localhost:8080/ifc/pull").callback(new LoadingNetCallback<List<String>>(mView) {
            @Override
            public void onSuccess(List<String> data) {
                super.onSuccess(data);
                mView.setPullDatas(data);
            }
        }).build());
    }
}
