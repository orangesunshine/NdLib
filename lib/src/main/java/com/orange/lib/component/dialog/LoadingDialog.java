package com.orange.lib.component.dialog;

import androidx.fragment.app.FragmentManager;

import com.orange.lib.R;
import com.orange.lib.component.dialog.base.BaseDialog;
import com.orange.lib.component.dialog.base.ViewHolder;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialogFragment;
import com.orange.lib.constance.IFinalConst;

public class LoadingDialog extends BaseDialog implements ILoadingDialogFragment {
    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {

    }

    @Override
    public int getContentLayoutId() {
        return R.layout.stub_layout_loading;
    }

    @Override
    public void showLoading(FragmentManager manager) {
        if (null != manager)
            show(manager, IFinalConst.FRAGMENT_LOADING_DIALOG);
    }

    @Override
    public void dismissLoading() {
        super.dismissAllowingStateLoss();
    }
}
