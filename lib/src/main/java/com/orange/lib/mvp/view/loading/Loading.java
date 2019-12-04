package com.orange.lib.mvp.view.loading;

import com.orange.lib.component.dialog.LoadingDialog;
import com.orange.lib.mvp.contact.view.BaseActivity;

import java.lang.ref.WeakReference;

public class Loading implements ILoading {
    private WeakReference<BaseActivity> mReference;
    private LoadingDialog mFragment;

    public Loading(BaseActivity activity) {
        mReference = new WeakReference<>(activity);
        mFragment = new LoadingDialog();
    }

    @Override
    public void showLoading() {
        if (null != mReference && null != mFragment) {
            BaseActivity baseActivity = mReference.get();
            if (null != baseActivity && baseActivity.isActivityAlive()) {
                mFragment.showLoading(baseActivity.getSupportFragmentManager());
            }
        }
    }

    @Override
    public void dismissLoading() {
        if (null != mReference && null != mFragment) {
            BaseActivity baseActivity = mReference.get();
            if (null != baseActivity && baseActivity.isActivityAlive()) {
                mFragment.dismissLoading();
            }
        }
    }
}
