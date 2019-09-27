package com.orange.lib.mvp.view.activity;

import android.os.Bundle;
import android.view.View;

import com.orange.lib.R;
import com.orange.lib.activity.Retry;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pagestatus.loading.dialogfragment.DefaultLoadingDialog;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;
import com.orange.lib.mvp.presenter.BasePresenter;
import com.orange.lib.mvp.presenter.NetPresenter;
import com.orange.lib.mvp.view.activity.base.BaseActivity;
import com.orange.lib.mvp.view.ifc.base.INetView;
import com.orange.lib.utils.base.Preconditions;

import java.lang.reflect.Method;

public abstract class NetActivity<P extends NetPresenter> extends BaseActivity implements INetView {
    //vars
    protected ILoadingDialog mLoading;//loading

    @Override
    protected void initVars(Bundle bundle) {
        super.initVars(bundle);
        mLoading = new DefaultLoadingDialog(this);
        if (!Preconditions.isNull(getPresenter()))
            getPresenter().initVars(bundle);
    }

    @Override
    protected void init() {
        super.init();
        //网络错误点击刷新
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                Class clazz = getClass();
                while (null != clazz) {
                    String name = clazz.getName();
                    if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")) {
                        break;
                    }
                    Method[] declaredMethods = clazz.getDeclaredMethods();
                    if (null != declaredMethods && declaredMethods.length > 0) {
                        for (Method declaredMethod : declaredMethods) {
                            if (null != declaredMethod) {
                                Retry retry = declaredMethod.getAnnotation(Retry.class);
                                if (null != retry) {
                                    try {
                                        declaredMethod.invoke(this);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }
                    clazz = clazz.getSuperclass();
                }
            }
        }, R.id.id_retry_orange);

        if (!Preconditions.isNull(getPresenter()))
            getPresenter().attachView(this);
    }

    /**
     * 根据占位类型插入占位视图（actbar，pull，content）
     */
    @Override
    protected void attachStub() {
        super.attachStub();//actbar

        //loading
        stubLayout(R.id.stub_loading_orange, R.layout.stub_layout_loading);

        //empty
        stubLayout(R.id.stub_empty_orange, R.layout.stub_layout_empty);

        //error
        stubLayout(R.id.stub_error_orange, R.layout.stub_layout_error);
    }

    protected abstract P getPresenter();

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        if (!Preconditions.isNull(mLoading))
            mLoading.dismissLoadingDialog();
        if (!Preconditions.isNull(getPresenter()))
            getPresenter().onActivityDestroy();
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (!Preconditions.isNull(mLoading))
            mLoading = new DefaultLoadingDialog(mActivity);
        if (isActivityAlive())
            mLoading.showLoadingDialog();
    }

    /**
     * 隐藏
     */
    @Override
    public void hideLoading() {
        if (!Preconditions.isNull(mLoading))
            mLoading = new DefaultLoadingDialog(mActivity);
        if (isActivityAlive())
            mLoading.dismissLoadingDialog();
    }
}
