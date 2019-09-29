package com.orange.ndlib.activity;

import com.orange.lib.mvp.view.activity.base.BaseActivity;
import com.orange.lib.utils.Activitys;
import com.orange.ndlib.R;
import com.orange.ndlib.activity.base.DemoActivity;
import com.orange.ndlib.activity.base.loading.LoadingDemoActivity;
import com.orange.ndlib.activity.base.loading.page.PageNetDemoActivity;

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
                    Activitys.launchActivity(mActivity, DemoActivity.class);
                    break;
                case R.id.btn_loading:
                    Activitys.launchActivity(mActivity, LoadingDemoActivity.class);
                    break;
                case R.id.btn_loading_page:
                    Activitys.launchActivity(mActivity, PageNetDemoActivity.class);
                    break;
            }
        }, R.id.btn_base, R.id.btn_loading, R.id.btn_loading_page);
    }
}
