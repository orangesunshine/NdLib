package com.orange.lib.component.pagestatus.loading.dialogfragment;

import com.orange.lib.mvp.view.activity.base.BaseActivity;
import com.orange.lib.component.dialog.LoadingDialog;

import java.lang.ref.WeakReference;

public class DefaultLoadingDialog implements ILoadingDialog {
    private WeakReference<BaseActivity> mReference;
    private ILoadingDialogFragment mFragment;

    public DefaultLoadingDialog(BaseActivity activity) {
        mReference = new WeakReference<>(activity);
        mFragment = new LoadingDialog();
    }

    @Override
    public void showLoadingDialog() {
        if (null != mReference && null != mFragment) {
            BaseActivity baseActivity = mReference.get();
            if (null != baseActivity && baseActivity.isActivityAlive()) {
                mFragment.showLoading(baseActivity.getSupportFragmentManager());
            }
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (null != mReference && null != mFragment) {
            BaseActivity baseActivity = mReference.get();
            if (null != baseActivity && baseActivity.isActivityAlive()) {
                mFragment.dismissLoading();
            }
        }
    }
}
