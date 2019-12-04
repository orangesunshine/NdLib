package com.orange.ndlib.activity.demo.pull;

import com.orange.lib.mvp.contact.view.PullActivity;
import com.orange.ndlib.R;

/**
 * @Author: orange
 * @CreateDate: 2019/10/3 11:55
 */
public class PullDemoActivity extends PullActivity<IPullDemoContact.Presenter> implements IPullDemoContact.View {
    @Override
    protected IPullDemoContact.Presenter getPresenter() {
        return new PullDemoActivityPresenter();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_pull_demo;
    }

    @Override
    public void setPullDatas() {

    }

    @Override
    public boolean isRefreshing() {
        return false;
    }

    @Override
    public boolean isLoadmore() {
        return false;
    }
}
