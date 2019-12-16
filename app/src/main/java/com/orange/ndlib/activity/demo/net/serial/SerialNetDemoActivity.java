package com.orange.ndlib.activity.demo.net.serial;

import android.view.View;

import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.contact.view.NetActivity;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.ndlib.R;

public class SerialNetDemoActivity extends NetActivity<ISerialNetDemoContact.Presenter> implements ISerialNetDemoContact.View {
    private INetCancel mINetCancel;

    @Override
    protected ISerialNetDemoContact.Presenter getPresenter() {
        return new SerialNetDemoPresenter();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_serial_net_demo;
    }

    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_serial_rb_net:
                        mINetCancel = mPresenter.getAllRbData();
                        break;
                    case R.id.btn_serial_t_net:
                        mINetCancel = mPresenter.getAllTData();
                        break;
                    case R.id.btn_serial_rb_t_net:
                        mINetCancel = mPresenter.getRbSerialTData();
                        break;
                    case R.id.btn_serial_t_rb_net:
                        mINetCancel = mPresenter.getTSerialRbData();
                        break;
                    case R.id.btn_serial_net_cancel:
                        if (null != mINetCancel)
                            mINetCancel.cancel();
                        break;
                }
            }
        }, R.id.btn_serial_rb_net, R.id.btn_serial_t_net, R.id.btn_serial_rb_t_net, R.id.btn_serial_t_rb_net, R.id.btn_serial_net_cancel);
    }

    @Override
    public void showSerialResult(String result) {
        mHolder.setText(R.id.tv_serial_ret, result);
    }
}
