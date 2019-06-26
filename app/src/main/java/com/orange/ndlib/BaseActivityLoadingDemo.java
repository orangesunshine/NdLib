package com.orange.ndlib;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.common.convert.IConvert;
import com.orange.lib.component.pagestatus.loading.dialogfragment.DefaultLoadingDialog;
import com.orange.lib.mvp.model.net.INetRequest;
import com.orange.lib.mvp.model.net.callback.INetCallback;
import com.orange.lib.mvp.model.net.callback.LoadingNetCallback;
import com.orange.lib.mvp.model.net.callback.PageStatusNetCallback;
import com.orange.lib.utils.NetUtils;
import com.orange.thirdparty.retrofit.RetrofitUrlApi;

import java.util.HashMap;

public class BaseActivityLoadingDemo extends BaseActivity {
    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_base_loading_demo;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        NetUtils.loadingNetData(new INetRequest() {
            @Override
            public void request(INetCallback callback) {
                RetrofitUrlApi.getInstance().post("http://localhost:8080/ifc/loading", new HashMap<>(), callback);
            }
        },new PageStatusNetCallback<String>(mPageStatus){
            /**
             * 网络请求成功
             *
             * @param s
             */
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                mHolder.setText(R.id.content_id, s);
            }
        });
//        NetUtils.loadingNetData(mLoading, new INetRequest() {
//            @Override
//            public void request(INetCallback callback) {
//                RetrofitUrlApi.getInstance().post("http://localhost:8080/ifc/loading", new HashMap<>(), callback);
//            }
//        }, new IConvert<String>() {
//            @Override
//            public void convert(String data) {
//                mHolder.setText(R.id.content_id, data);
//            }
//        });
    }
}
