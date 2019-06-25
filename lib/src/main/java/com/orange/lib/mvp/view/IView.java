package com.orange.lib.mvp.view;


import com.orange.lib.component.pagestatus.IPageStatus;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;
import com.orange.lib.component.toast.IToast;

public interface IView extends IToast, IPageStatus, ILoadingDialog {

}
