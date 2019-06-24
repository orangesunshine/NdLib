package com.orange.lib.component.pagestatus.loading.dialogfragment;

import androidx.fragment.app.FragmentManager;

public interface ILoadingDialogFragment {
    /**
     * 显示loading
     * @param manager
     */
    void showLoading(FragmentManager manager);

    void dismissLoading();
}
