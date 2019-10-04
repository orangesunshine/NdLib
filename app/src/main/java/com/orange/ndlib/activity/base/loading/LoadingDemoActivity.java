package com.orange.ndlib.activity.base.loading;

import android.view.View;

import com.orange.lib.activity.Retry;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.view.activity.NetActivity;
import com.orange.ndlib.R;

public class LoadingDemoActivity extends NetActivity<ILoadingDemoContact.Presenter> implements ILoadingDemoContact.View {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_loading_demo;
    }

    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_show_loading:
                        showLoading();
                        break;
                    case R.id.btn_hide_loading:
                        hideLoading();
                        break;
                    case R.id.btn_data:
                        mPresenter.getLoadingData();
                        break;
                    case R.id.btn_multi:
                        mPresenter.getMultiDatas();
                        break;
                }
            }
        }, R.id.btn_show_loading, R.id.btn_hide_loading, R.id.btn_data, R.id.btn_multi);
    }

    @Override
    protected ILoadingDemoContact.Presenter getPresenter() {
        return new LoadingDemoPresenter();
    }

    @Retry
    public void test() {
        showMsg("test");
    }

    @Override
    public void setLoadingData(String data) {
        mHolder.setText(R.id.tv_content, data);
    }

    @Override
    public void setLoading2Data(String data) {
        mHolder.setText(R.id.tv_multi, data);
    }
}
