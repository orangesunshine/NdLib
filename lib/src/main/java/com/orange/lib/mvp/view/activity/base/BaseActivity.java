package com.orange.lib.mvp.view.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.orange.lib.common.config.DefaultConfig;
import com.orange.lib.common.holder.DefaultHolder;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.actbar.IActionBar;
import com.orange.lib.component.statusbar.IStatusBar;
import com.orange.lib.component.toast.DefaultToast;
import com.orange.lib.component.toast.IToast;
import com.orange.lib.mvp.view.IView;


public abstract class BaseActivity<V extends IView> extends FragmentActivity{
    // <editor-fold defaultstate="collapsed" desc="初始化变量">
    protected BaseActivity mActivity;
    protected IToast mToast;
    protected IHolder mHolder;
    protected IStatusBar mStatusBar;
    protected IActionBar mActionBar;

    private boolean mActivityAlive;

    /**
     * 初始化
     *
     * @param bundle
     */
    public void initVars(Bundle bundle) {
        mActivity = this;
        mActivityAlive = true;
        mHolder = new DefaultHolder(getWindow().getDecorView().findViewById(android.R.id.content));
//        mStatusBar = StatusBarTranslucent.getInstance();
//        mActionBar = buildActionBar();
        mToast = new DefaultToast();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="生命周期回调方法">

    /**
     * 创建生命周期回调
     *
     * @param activity
     * @param bundle
     */
    public void onActivityCreate(Activity activity, Bundle bundle) {
        FrameLayout content = getWindow().getDecorView().findViewById(android.R.id.content);
        content.removeAllViews();
//        LayoutInflater.from(activity).inflate(getContentLayoutId(), content, true);

        //初始化操作
        initVars(bundle);

        //statusbar
        mStatusBar.setStatusBar(activity);

        //toast
        if (null == mToast)
            mToast = DefaultConfig.getInstance().buildToast();

        //初始化
        init();
    }


    /**
     * 销毁生命周期回调
     *
     * @param context
     */
    public void onActivityDestroy(BaseActivity context) {
        mActivityAlive = false;
        if (null != mHolder)
            mHolder.clear();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="判断activity 是不是活的">

    /**
     * 判断activity 是不是活的
     *
     * @return
     */
    public boolean isActivityAlive() {
        return mActivityAlive;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="创建标题栏">

    /**
     * 创建标题栏
     *
     * @return
     */
//    @Override
//    public IActionBar buildActionBar() {
//        return new CommonActionBar(mHolder);
//    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="初始化（控件相关）">

    /**
     * 初始化（控件相关）
     */
    public void init() {

    }
    // </editor-fold>
}
