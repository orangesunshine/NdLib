package com.orange.ndlib.activity.demo.net;

import android.view.View;

import com.orange.lib.activity.retry.Retry;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.contact.view.NetActivity;
import com.orange.lib.mvp.view.page.loading.IPage;
import com.orange.lib.mvp.view.page.loading.LoadingDialogPage;
import com.orange.lib.mvp.view.page.loading.LoadingPage;
import com.orange.lib.utils.Activitys;
import com.orange.ndlib.R;
import com.orange.ndlib.activity.demo.net.parallel.ParallelNetDemoActivity;
import com.orange.ndlib.activity.demo.net.serial.SerialNetDemoActivity;

public class NetDemoActivity extends NetActivity<INetDemoContact.Presenter> implements INetDemoContact.View {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_loading_demo;
    }

    @Override
    protected INetDemoContact.Presenter getPresenter() {
        return new NetDemoPresenter();
    }

    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_show_loading:
                        mPresenter.pageLoading();
                        break;
                    case R.id.btn_show_dialog_loading:
                        mPresenter.pageDialogLoading();
                        break;
                    case R.id.btn_hide_loading:
                        mPage.dismissLoading();
                        break;
                    case R.id.btn_show_content:
                        mPresenter.pageContent();
                        break;
                    case R.id.btn_show_error:
                        mPresenter.pageError();
                        break;
                    case R.id.btn_serial_enter:
                        Activitys.launchActivity(mActivity, SerialNetDemoActivity.class);
                        break;
                    case R.id.btn_parallel_enter:
                        Activitys.launchActivity(mActivity, ParallelNetDemoActivity.class);
                        break;
                }
            }
        }, R.id.btn_show_loading, R.id.btn_hide_loading, R.id.btn_show_dialog_loading, R.id.btn_show_content, R.id.btn_show_error, R.id.btn_serial_enter, R.id.btn_parallel_enter);
    }

    @Override
    protected IPage getPage() {
        return new LoadingPage(mHolder);
    }

    @Override
    public void showLoading() {
        mPage = new LoadingPage(mHolder);
        mPage.showLoading();
    }

    @Retry
    public void test() {
        msg("test");
    }

    @Override
    public void showDialogLoading() {
        mPage = new LoadingDialogPage(mLoading, mHolder);
        mPage.showLoading();
    }

    @Override
    public void setLoadingData(String data) {
        mHolder.setText(R.id.tv_content, data);
    }
}
