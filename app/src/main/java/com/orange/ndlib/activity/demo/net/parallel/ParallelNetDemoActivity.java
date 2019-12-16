package com.orange.ndlib.activity.demo.net.parallel;

import com.orange.lib.mvp.contact.view.NetActivity;

public class ParallelNetDemoActivity extends NetActivity<IParallelNetDemoContact.Presenter> implements IParallelNetDemoContact.View {
    @Override
    protected IParallelNetDemoContact.Presenter getPresenter() {
        return new ParallelNetDemoPresenter();
    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    public void showSerialResult(String result) {

    }
}
