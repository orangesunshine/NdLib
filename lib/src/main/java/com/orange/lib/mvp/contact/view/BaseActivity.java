package com.orange.lib.mvp.contact.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.fragment.app.FragmentActivity;

import com.orange.lib.R;
import com.orange.lib.common.adapterpattern.ActionBarCallbackAdapter;
import com.orange.lib.common.config.Config;
import com.orange.lib.common.holder.CommonHolder;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.actbar.CommonActionBar;
import com.orange.lib.component.actbar.IActionBar;
import com.orange.lib.component.statusbar.IStatusBar;
import com.orange.lib.component.statusbar.SystemBarTintManager;
import com.orange.lib.component.toast.CommonToast;
import com.orange.lib.utils.Preconditions;
import com.orange.lib.utils.view.Views;
import com.orange.utils.common.Colors;

/**
 * 基础activity：actbar、statusbar
 * 模板方法：组装视图、插入占位布局、初始化
 *
 * @method onActivityCreate、onActivityDestroy application->lifecycle回调
 */
public abstract class BaseActivity extends FragmentActivity {
    //final
    protected final String TAG = getClass().getSimpleName();

    //views
    protected IHolder mHolder;//view容器
    protected IActionBar mActbar;//标题栏
    protected IStatusBar mStatusBar;//状态栏

    //vars
    protected BaseActivity mActivity;//activity引用
    protected boolean isActivityAlive;//判断activity是不是活的

    /**
     * onCreate生命周期调用
     */
    public void onActivityCreated(Bundle bundle) {
        initVars(bundle);//初始化变量

        FrameLayout content = getWindow().getDecorView().findViewById(android.R.id.content);

        mHolder = new CommonHolder(content);//控件容器

        attachView(content);//视图

        mHolder.setVisible(R.id.id_statusbar_orange, translucentStatusBar(), false);
        statusBar();//状态栏

        attachStub();//占位

        init();//初始化
    }

    /**
     * 初始化变量
     *
     * @param bundle from OnCreate
     */
    protected void initVars(Bundle bundle) {
        mActivity = this;
        isActivityAlive = true;
    }

    /**
     * 设置状态栏颜色
     */
    protected void statusBar() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            Views.setStatusBarHeight(mHolder.getView(R.id.id_statusbar_orange));
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else {
            setStatusBarColor(Colors.getRandomColor());
        }
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return true;
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected void setStatusBarColor(@ColorInt int color) {
        // 沉浸式状态栏
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(color);
        }
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
            Config.getInstance().getLog().e("-1 == contentLayoutId");
        LayoutInflater.from(this).inflate(contentLayoutId, content, true);

        if (null != mHolder.getView(R.id.id_stub_content_orange))
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
        Views.attachStub(mHolder.getView(R.id.id_stub_actbar_orange), R.layout.stub_layout_actbar_common);
        mActbar = new CommonActionBar(mHolder);
        mActbar.setLeftImg(R.drawable.ic_arrow_back_black_24dp);
        mActbar.setActionBarCallback(new ActionBarCallbackAdapter() {
            @Override
            public void onLeft() {
                onBackPressed();
            }
        });
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (null != ev && MotionEvent.ACTION_DOWN == ev.getActionMasked()) {
            CommonToast.cancelToasts4Touch();
        }
        return super.dispatchTouchEvent(ev);
    }
}
