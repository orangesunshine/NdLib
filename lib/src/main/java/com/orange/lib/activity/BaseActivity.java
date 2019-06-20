package com.orange.lib.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.orange.lib.common.holder.DefaultHolder;
import com.orange.lib.common.holder.IHolder;

/**
 * 基础activity
 *
 * @method onActivityCreate、onActivityDestroy application->lifecycle回调
 */
public abstract class BaseActivity extends FragmentActivity {
    protected BaseActivity mActivity;//activity引用
    protected boolean isActivityAlive;//判断activity是不是活的
    protected IHolder mHolder;//view容器


    /**
     * onCreate生命周期调用
     */
    private void onActivityCreate(Bundle bundle) {
        FrameLayout content = getWindow().getDecorView().findViewById(android.R.id.content);
        int contentLayoutId = getTempleteLayoutId();
        if (-1 == contentLayoutId)
            contentLayoutId = getContentLayoutId();
        if (-1 == contentLayoutId)
            throw new IllegalArgumentException("-1 == contentLayoutId");
        LayoutInflater.from(mActivity).inflate(getContentLayoutId(), content, true);
        initVars(content, bundle);
        templeteLayout(contentLayoutId);
        init();
    }

    /**
     * 处理模板布局文件
     */
    protected void templeteLayout(int templeteContentLayoutId) {
    }

    /**
     * 获取模板布局文件
     *
     * @return
     */
    protected int getTempleteLayoutId() {
        return -1;
    }

    /**
     * 初始化控件
     */
    protected abstract void init();

    /**
     * 初始化变量
     *
     * @param contentView
     * @param bundle
     */
    protected void initVars(FrameLayout contentView, Bundle bundle) {
        mActivity = this;
        isActivityAlive = true;
        mHolder = new DefaultHolder(contentView);
    }

    /**
     * onDestory生命周期调用
     */
    private void onActivityDestroy() {
        mActivity = null;
        isActivityAlive = false;
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    protected int getContentLayoutId() {
        return -1;
    }
}
