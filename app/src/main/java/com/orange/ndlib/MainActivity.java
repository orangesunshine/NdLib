package com.orange.ndlib;

import android.widget.FrameLayout;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.activity.templete.TempleteActivity;
import com.orange.lib.utils.ActivityUtils;
import com.orange.thirdparty.statusbar.StatusBarTranslucent;

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
     * 插入视图
     *
     * @param content
     */
    @Override
    protected void attachView(FrameLayout content) {
        StatusBarTranslucent.getInstance().setStatusBar(this);
        super.attachView(content);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        mActbar.setTitle("MainActivity");
        mHolder.addOnItemChildClick(v -> {
            switch (v.getId()) {
                case R.id.btn_base:
                    ActivityUtils.launchActivity(mActivity, BaseActivityDemo.class);
                    break;
                case R.id.btn_templete:
                    ActivityUtils.launchActivity(mActivity, TempleteActivity.class);
                    break;
            }
        }, R.id.btn_base, R.id.btn_templete);
    }
}
