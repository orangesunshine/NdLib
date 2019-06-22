package com.orange.lib.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.orange.lib.common.holder.DefaultHolder;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.toast.DefaultToast;
import com.orange.lib.component.toast.IToast;

/**
 * 基础activity
 *
 * @method onActivityCreate、onActivityDestroy application->lifecycle回调
 */
public abstract class BaseActivity extends FragmentActivity {
    protected BaseActivity mActivity;//activity引用
    protected boolean isActivityAlive;//判断activity是不是活的
    protected IHolder mHolder;//view容器
    protected IToast mToast;

    /**
     * onCreate生命周期调用
     */
    public void onActivityCreate(Bundle bundle) {
        FrameLayout content = getWindow().getDecorView().findViewById(android.R.id.content);
        int contentLayoutId = getContentLayoutId();
        if (-1 == contentLayoutId)
            throw new IllegalArgumentException("-1 == contentLayoutId");
        LayoutInflater.from(this).inflate(contentLayoutId, content, true);
        mHolder = new DefaultHolder(content);
        initVars(bundle);
        init();
    }

    /**
     * 初始化控件
     */
    protected void init() {
        //1.状态栏
    }

    /**
     * 初始化变量
     *
     * @param bundle
     */
    protected void initVars(Bundle bundle) {
        mActivity = this;
        isActivityAlive = true;
        mToast = new DefaultToast();//吐司
    }

    /**
     * onDestory生命周期调用
     */
    public void onActivityDestroy() {
        mActivity = null;
        isActivityAlive = false;
        if (null != mHolder)
            mHolder.clear();
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getContentLayoutId();
}
