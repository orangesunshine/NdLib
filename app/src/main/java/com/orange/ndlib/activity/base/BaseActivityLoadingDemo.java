package com.orange.ndlib.activity.base;

import android.view.View;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.common.convert.IConvert;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.constance.IConst;
import com.orange.lib.loading.pagestatus.IPageStatus;
import com.orange.lib.loading.pagestatus.LoadingDialogPageStatus;
import com.orange.lib.loading.callback.PageStatusNetCallback;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.utils.net.NetUtils;
import com.orange.ndlib.R;
import com.orange.thirdparty.retrofit.api.RetrofitUrlApi;

import java.util.HashMap;

public class BaseActivityLoadingDemo extends BaseActivity {
    private INetCancel mNetCancel;

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
        IPageStatus pageStatus = new LoadingDialogPageStatus(mLoading, mHolder);
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_loading_convert:
                        mNetCancel = NetUtils.loadingNetData(callback -> RetrofitUrlApi.getInstance().post("http://localhost:8080/ifc/loading", new HashMap<>(), callback), mLoading, (IConvert<String>) data -> {
                            mHolder.setText(R.id.tv_content, data);
                        });
                        break;
                    case R.id.btn_loading_page:
                        mNetCancel = NetUtils.loadingNetData(callback -> RetrofitUrlApi.getInstance().post("http://localhost:8080/ifc/loading", new HashMap<>(), callback), new PageStatusNetCallback<String>(pageStatus) {
                            /**
                             * 网络请求成功
                             *
                             * @param s
                             */
                            @Override
                            public void onSuccess(String s) {
                                super.onSuccess(s);
                                mHolder.setText(R.id.tv_content, s);
                            }
                        });
                        break;
                    case R.id.btn_loading_page_convert:
                        mNetCancel = NetUtils.loadingNetData(callback -> RetrofitUrlApi.getInstance().post(IConst.sBaseUrl + "/ifc/loading", new HashMap<>(), callback), pageStatus, (IConvert<String>) data -> {
                            mHolder.setText(R.id.tv_content, data);
                        });
                        break;
                }
            }
        }, R.id.btn_loading_convert, R.id.btn_loading_page, R.id.btn_loading_page_convert);
    }

    /**
     * onDestory生命周期调用
     */
    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        if (null != mNetCancel)
            mNetCancel.cancel();
    }
}
