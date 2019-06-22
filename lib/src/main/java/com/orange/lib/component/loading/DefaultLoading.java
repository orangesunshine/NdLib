package com.orange.lib.component.loading;

import com.orange.lib.component.dialog.LoadingDialog;
import com.orange.lib.mvp.view.activity.base.BaseActivity;

import java.lang.ref.WeakReference;

public class DefaultLoading implements ILoading {
    private WeakReference<BaseActivity> mReference;
    private ILoadingDialogFragment mFragment;

    public DefaultLoading(BaseActivity activity) {
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
