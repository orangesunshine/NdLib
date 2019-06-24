package com.orange.lib.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.orange.lib.R;
import com.orange.lib.common.holder.DefaultHolder;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.actbar.CommonActionBar;
import com.orange.lib.component.actbar.IActionBar;
import com.orange.lib.component.pull.IPull;
import com.orange.lib.component.toast.DefaultToast;
import com.orange.lib.component.toast.IToast;
import com.orange.lib.mvp.presenter.ifc.IPresenter;
import com.orange.lib.mvp.view.IView;

/**
 * 基础activity
 *
 * @method onActivityCreate、onActivityDestroy application->lifecycle回调
 */
public abstract class BaseActivity<P extends IPresenter> extends FragmentActivity implements IView {
    protected BaseActivity mActivity;//activity引用
    protected boolean isActivityAlive;//判断activity是不是活的
    protected IHolder mHolder;//view容器
    protected IToast mToast;//吐司
    protected P mPresenter;//mvp
    protected IActionBar mActbar;//标题栏
    protected IPull mPull;//下拉、加载

    /**
     * onCreate生命周期调用
     */
    public void onActivityCreate(Bundle bundle) {
        //初始化变量
        initVars(bundle);

        FrameLayout content = getWindow().getDecorView().findViewById(android.R.id.content);
        //控件容器
        mHolder = new DefaultHolder(content);

        //视图
        attachView(content);

        //mvp
        mPresenter = getPresenter();
        if (null != mPresenter)
            mPresenter.attachView(this);

        //初始化
        init();
    }

    /**
     * 插入视图
     *
     * @param content
     */
    protected void attachView(FrameLayout content) {
        int contentLayoutId = getContentLayoutId();
        if (-1 == contentLayoutId)
            throw new IllegalArgumentException("-1 == contentLayoutId");
        LayoutInflater.from(this).inflate(contentLayoutId, content, true);

        if (null != mHolder.getView(R.id.stub_content))
            throw new IllegalArgumentException("BaseActivity 不能再次插入content试图");
        attachStub();
    }

    /**
     * 根据占位类型插入占位视图（actbar，pull，content）
     */
    protected void attachStub() {
        //actbar占位
        ViewStub actbarStub = mHolder.getView(R.id.stub_actbar);
        if (null != actbarStub) {
            actbarStub.setLayoutResource(R.layout.stub_actbar_common);
            actbarStub.inflate();
            mActbar = new CommonActionBar(mHolder);
        }

        //swippull
        ViewStub swiprefreshStub = mHolder.getView(R.id.stub_swiprefresh);
        if (null != swiprefreshStub) {
            swiprefreshStub.setLayoutResource(R.layout.stub_swiperefreshlayout);
            swiprefreshStub.inflate();
        }
    }

    /***
     * 获取presenter
     * @return
     */
    protected P getPresenter() {
        return null;
    }

    /**
     * 初始化控件
     */
    protected void init() {
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

    /**
     * 吐司
     *
     * @param text 消息
     */
    @Override
    public void showToast(CharSequence text) {
        if (null != mToast)
            mToast.showToast(text);
    }

    @Override
    public void showToast(int stringId) {
        if (null != mToast)
            mToast.showToast(stringId);
    }
}
