package com.orange.lib.mvp.view.activity;

import android.os.Bundle;
import android.view.View;

import com.orange.lib.R;
import com.orange.lib.activity.Retry;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.mvp.presenter.ifc.IPresenter;
import com.orange.lib.mvp.view.activity.base.BaseActivity;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.mvp.view.impl.Loading;
import com.orange.lib.utils.base.Preconditions;

import java.lang.reflect.Method;

public abstract class NetActivity<P extends IPresenter & IUrlApi> extends BaseActivity implements ILoading {
    //vars
    protected ILoading mLoading;//loading
    protected P mPresenter;

    @Override
    protected void initVars(Bundle bundle) {
        super.initVars(bundle);
        mLoading = getLoading();
        if (null == mLoading)
            mLoading = new Loading(mActivity);

        //presenter关联视图
        mPresenter = getPresenter();
        if (!Preconditions.isNull(mPresenter)) {
            mPresenter.attachView(this);
            mPresenter.initVars(bundle);
        }
    }

    /**
     * 自定义实现loading
     *
     * @return
     */
    protected ILoading getLoading() {
        return null;
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
            mLoading.hideLoading();
        if (!Preconditions.isNull(mPresenter))
            mPresenter.onActivityDestroy();
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (Preconditions.isNull(mLoading))
            mLoading = new Loading(mActivity);
        if (isActivityAlive())
            mLoading.showLoading();
    }

    /**
     * 隐藏
     */
    @Override
    public void hideLoading() {
        if (isActivityAlive() && !Preconditions.isNull(mLoading))
            mLoading.hideLoading();
    }
}
