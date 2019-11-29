package com.orange.ndlib.activity.demo.presenter;

import com.orange.lib.mvp.contact.IContact;
import com.orange.lib.mvp.contact.view.PresenterActivity;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 17:28
 */
public class PresenterDemoActivity extends PresenterActivity {
    @Override
    protected IContact.Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }
}
