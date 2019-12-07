package com.orange.ndlib.activity;

import com.orange.lib.mvp.contact.view.BaseActivity;
import com.orange.lib.utils.Activitys;
import com.orange.ndlib.R;
import com.orange.ndlib.activity.demo.base.BaseDemoActivity;
import com.orange.ndlib.activity.demo.net.NetDemoActivity;
import com.orange.ndlib.activity.demo.presenter.PresenterDemoActivity;

public class MainActivity extends BaseActivity {
    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(v -> {
            switch (v.getId()) {
                case R.id.btn_base:
                    Activitys.launchActivity(mActivity, BaseDemoActivity.class);
                    break;
                case R.id.btn_presenter:
                    Activitys.launchActivity(mActivity, PresenterDemoActivity.class);
                    break;
                case R.id.btn_loading:
                    Activitys.launchActivity(mActivity, NetDemoActivity.class);
                    break;
                case R.id.btn_loading_page:
                    break;
            }
        }, R.id.btn_base, R.id.btn_presenter, R.id.btn_loading, R.id.btn_loading_page);
    }
}
