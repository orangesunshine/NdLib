package com.orange.lib.component.pagestatus.loading.dialogfragment;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.component.dialog.LoadingDialog;

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
