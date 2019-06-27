package com.orange.lib.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.orange.lib.R;
import com.orange.lib.common.holder.DefaultHolder;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.actbar.CommonActionBar;
import com.orange.lib.component.actbar.IActionBar;
import com.orange.lib.component.pagestatus.IPageStatus;
import com.orange.lib.component.pagestatus.loading.dialogfragment.DefaultLoadingDialog;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;
import com.orange.lib.component.pagestatus.loading.dialogfragment.LoadingDialogPageStatus;
import com.orange.lib.component.toast.DefaultToast;
import com.orange.lib.component.toast.IToast;
import com.orange.lib.mvp.presenter.ifc.IPresenter;
import com.orange.lib.mvp.view.IView;

import java.lang.reflect.Method;

/**
 * 基础activity
 *
 * @method onActivityCreate、onActivityDestroy application->lifecycle回调
 */
public abstract class BaseActivity<P extends IPresenter> extends FragmentActivity implements IView {
    protected final String TAG = getClass().getSimpleName();
    protected BaseActivity mActivity;//activity引用
    protected boolean isActivityAlive;//判断activity是不是活的
    protected IHolder mHolder;//view容器
    protected IToast mToast;//吐司
    protected P mPresenter;//mvp
    protected IActionBar mActbar;//标题栏
    protected ILoadingDialog mLoading;
    protected IPageStatus mPageStatus;

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

        //占位
        attachStub();

        //mvp
        mPresenter = getPresenter();
        if (null != mPresenter)
            mPresenter.attachView(this);

        //初始化
        init();
    }

    /**
     * 判断activity是不是活的
     *
     * @return
     */
    public boolean isActivityAlive() {
        return isActivityAlive;
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
    }

    /**
     * 根据占位类型插入占位视图（actbar，pull，content）
     */
    protected void attachStub() {
        //actbar占位
        ViewStub actbarStub = mHolder.getView(R.id.stub_actbar);
        if (null != actbarStub) {
            actbarStub.setLayoutResource(R.layout.stub_layout_actbar_common);
            actbarStub.inflate();
        }

        //loading
        ViewStub loadingStub = mHolder.getView(R.id.stub_loading);
        if (null != loadingStub) {
            loadingStub.setLayoutResource(R.layout.stub_layout_loading);
            loadingStub.inflate();
        }

        //empty
        ViewStub emptyStub = mHolder.getView(R.id.stub_empty);
        if (null != emptyStub) {
            emptyStub.setLayoutResource(R.layout.stub_layout_empty);
            emptyStub.inflate();
        }

        //error
        ViewStub errorStub = mHolder.getView(R.id.stub_error);
        if (null != errorStub) {
            errorStub.setLayoutResource(R.layout.stub_layout_error);
            errorStub.inflate();
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
        mActbar = new CommonActionBar(mHolder);
        mActbar.setTitle(TAG);
        mPageStatus = createPageStatus();
        mHolder.addOnItemChildClick(v -> {
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
        }, R.id.retry_button);
    }

    /**
     * 创建pagestatus
     *
     * @return
     */
    protected IPageStatus createPageStatus() {
        return new LoadingDialogPageStatus(mLoading, mHolder);
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
        mLoading = new DefaultLoadingDialog(this);
    }

    /**
     * onDestory生命周期调用
     */
    public void onActivityDestroy() {
        mActivity = null;
        isActivityAlive = false;
        if (null != mHolder)
            mHolder.clear();
        if (null != mLoading)
            mLoading.dismissLoadingDialog();
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

    /**
     * 显示loading
     */
    @Override
    public void showLoadingDialog() {
        if (null == mLoading)
            mLoading = new DefaultLoadingDialog(mActivity);
        if (isActivityAlive)
            mLoading.showLoadingDialog();
    }

    /**
     * 隐藏
     */
    @Override
    public void dismissLoadingDialog() {
        if (null == mLoading)
            mLoading = new DefaultLoadingDialog(mActivity);
        if (isActivityAlive)
            mLoading.dismissLoadingDialog();
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (null != mPageStatus)
            mPageStatus.showLoading();
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        if (null != mPageStatus)
            mPageStatus.showContent();
    }

    /**
     * 显示empty
     */
    @Override
    public void showEmpty() {
        if (null != mPageStatus)
            mPageStatus.showEmpty();
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        if (null != mPageStatus)
            mPageStatus.showError();
    }

    @Retry
    public void test() {
        Toast.makeText(mActivity, TAG, Toast.LENGTH_SHORT).show();
    }
}
