package com.orange.lib.mvp.view.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.orange.lib.R;
import com.orange.lib.common.holder.CommonHolder;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.actbar.CommonActionBar;
import com.orange.lib.component.actbar.IActionBar;
import com.orange.lib.component.statusbar.IStatusBar;
import com.orange.lib.mvp.view.ifc.base.IView;
import com.orange.lib.utils.base.Preconditions;
import com.orange.lib.utils.log.Logs;
import com.orange.lib.utils.toast.Toasts;

/**
 * 基础activity：actbar、statusbar
 * 模板方法：组装视图、插入占位布局、初始化
 *
 * @method onActivityCreate、onActivityDestroy application->lifecycle回调
 */
public abstract class BaseActivity extends FragmentActivity implements IView {
    protected final String TAG = getClass().getSimpleName();
    protected BaseActivity mActivity;//activity引用
    protected boolean isActivityAlive;//判断activity是不是活的
    protected IHolder mHolder;//view容器
    protected IActionBar mActbar;//标题栏
    protected IStatusBar mStatusBar;//状态栏

    /**
     * onCreate生命周期调用
     */
    public void onActivityCreate(Bundle bundle) {
        initVars(bundle);//初始化变量

        statusBar();//状态栏

        FrameLayout content = getWindow().getDecorView().findViewById(android.R.id.content);

        mHolder = new CommonHolder(content);//控件容器

        attachView(content);//视图

        attachStub();//占位

        init();//初始化
    }

    protected void initVars(Bundle bundle) {
        mActivity = this;
        isActivityAlive = true;
    }

    /**
     * 状态栏
     */
    protected void statusBar() {
//        mStatusBar = StatusBarTranslucent.getInstance();
//        if (!Preconditions.isNull(mStatusBar))
//            mStatusBar.setStatusBar(mActivity);
    }

    /**
     * 判断activity是不是活的
     *
     * @return
     */
    public boolean isActivityAlive() {
        boolean ret = isActivityAlive && !isFinishing();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ret &= !isDestroyed();
        }
        return ret;
    }

    /**
     * 插入视图
     *
     * @param content
     */
    protected void attachView(FrameLayout content) {
        int contentLayoutId = getContentLayoutId();
        if (-1 == contentLayoutId)
            Logs.toge("-1 == contentLayoutId");
        LayoutInflater.from(this).inflate(contentLayoutId, content, true);

        if (null != mHolder.getView(R.id.stub_content_orange))
            throw new IllegalArgumentException("BaseActivity 再次attach content视图");
    }

    /**
     * 根据占位类型插入占位视图（actbar，pull，content）
     */
    protected void attachStub() {
        //actbar
        attachActbar();

//        //swippull
//        stubLayout(R.id.stub_swiprefresh_orange, R.layout.stub_swiperefreshlayout);
    }

    /**
     * 注入actbar布局到actbar占位，并初始化actbar
     */
    protected void attachActbar() {
        //actbar占位
        stubLayout(R.id.stub_actbar_orange, R.layout.stub_layout_actbar_common);
        mActbar = new CommonActionBar(mHolder);
    }

    /**
     * 布局文件注入到stub占位
     *
     * @param stubId
     * @param stubLayoutId
     */
    protected void stubLayout(int stubId, int stubLayoutId) {
        ViewStub stub = mHolder.getView(stubId);
        if (!Preconditions.isNull(stub)) {
            stub.setLayoutResource(stubLayoutId);
            stub.inflate();
        }
    }

    /**
     * 初始化控件
     */
    protected void init() {
        mActbar.setTitle(TAG);
    }

    /**
     * onDestory生命周期调用
     */
    public void onActivityDestroy() {
        mActivity = null;
        isActivityAlive = false;
        if (!Preconditions.isNull(mHolder))
            mHolder.clear();
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getContentLayoutId();

    @Override
    public void showMsg(CharSequence charSequence) {
        Toasts.showMsg(charSequence);
    }

    @Override
    public void showMsg(int stringId) {
        Toasts.showMsg(getResources().getText(stringId));
    }
}
