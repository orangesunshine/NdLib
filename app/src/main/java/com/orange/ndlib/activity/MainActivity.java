package com.orange.ndlib.activity;

import com.orange.lib.mvp.view.activity.base.BaseActivity;
import com.orange.lib.utils.Activitys;
import com.orange.ndlib.CocosWebActivity;
import com.orange.ndlib.R;
import com.orange.ndlib.activity.base.BaseActivityDemo;
import com.orange.ndlib.activity.templete.TempleteDemoActivity;

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
                    Activitys.launchActivity(mActivity, BaseActivityDemo.class);
                    break;
                case R.id.btn_templete:
                    Activitys.launchActivity(mActivity, TempleteDemoActivity.class);
                    break;
                case R.id.btn_cocos:
                    Activitys.launchActivity(mActivity, CocosWebActivity.class);
                    break;
            }
        }, R.id.btn_base, R.id.btn_templete, R.id.btn_cocos);
    }
}
