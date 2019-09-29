package com.orange.ndlib.activity.base.loading;

import com.orange.lib.loading.callback.LoadingNetCallback;
import com.orange.lib.loading.request.NetRequest;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.NetPresenter;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:06
 */
public class LoadingDemoPresenter extends NetPresenter<ILoadingDemoContact.View> implements ILoadingDemoContact.Presenter {
    @Override
    public INetCancel getLoadingData() {
        return singleRequest(NetRequest.NetBuilder.builder().url("http://localhost:8080/ifc/loading1").callback(new LoadingNetCallback<String>(mView) {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                mView.setLoadingData(data);
            }
        }).build());
    }
}
