package com.orange.ndlib.activity.demo.net.parallel;

import android.view.View;

import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.contact.view.NetActivity;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.ndlib.R;

public class ParallelNetDemoActivity extends NetActivity<IParallelNetDemoContact.Presenter> implements IParallelNetDemoContact.View {
    private INetCancel mINetCancel;

    @Override
    protected IParallelNetDemoContact.Presenter getPresenter() {
        return new ParallelNetDemoPresenter();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_parallel_net_demo;
    }

    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_parallel_rb_net:
                        mINetCancel = mPresenter.getAllRbData();
                        break;
                    case R.id.btn_parallel_t_net:
                        mINetCancel = mPresenter.getAllTData();
                        break;
                    case R.id.btn_parallel_rb_t_net:
                        mINetCancel = mPresenter.getRbParallelTData();
                        break;
                    case R.id.btn_parallel_t_rb_net:
                        mINetCancel = mPresenter.getTParallelRbData();
                        break;
                    case R.id.btn_parallel_net_cancel:
                        if (null != mINetCancel)
                            mINetCancel.cancel();
                        break;
                }
            }
        }, R.id.btn_parallel_rb_net, R.id.btn_parallel_t_net, R.id.btn_parallel_rb_t_net, R.id.btn_parallel_t_rb_net, R.id.btn_parallel_net_cancel);
    }

    @Override
    public void showParallelResult(String result) {

    }
}
