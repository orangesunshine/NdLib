package com.orange.lib.component.dialog;

import androidx.fragment.app.FragmentManager;

import com.orange.lib.R;
import com.orange.lib.component.dialog.base.BaseDialog;
import com.orange.lib.component.dialog.base.ViewHolder;
import com.orange.lib.constance.IConst;

public class LoadingDialog extends BaseDialog {
    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {

    }

    @Override
    public int getContentLayoutId() {
        return R.layout.stub_layout_loading;
    }

    public void showLoading(FragmentManager manager) {
        if (null != manager)
            show(manager, IConst.FRAGMENT_LOADING_DIALOG);
    }

    public void dismissLoading() {
        if (isAdded())
            super.dismissAllowingStateLoss();
    }
}
