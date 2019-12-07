package com.orange.ndlib.activity.demo.presenter;

import android.view.View;

import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.contact.view.PresenterActivity;
import com.orange.ndlib.R;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 17:28
 */
public class PresenterDemoActivity extends PresenterActivity<IPresenterDemoContact.Presenter> implements IPresenterDemoContact.View {
    @Override
    protected IPresenterDemoContact.Presenter getPresenter() {
        return new PresenterDemoPresenter();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_presenter;
    }

    @Override
    public void showPresenterMsg(String msg) {
        msg(msg);
    }

    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                mPresenter.presenterTest();
            }
        }, R.id.btn_presenter);
    }
}
