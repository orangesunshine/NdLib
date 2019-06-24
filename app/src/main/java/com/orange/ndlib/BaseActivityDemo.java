package com.orange.ndlib;

import com.orange.lib.activity.BaseActivity;

public class BaseActivityDemo extends BaseActivity {
    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.template_activity_actbar;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        mActbar.setLeftText("哈哈哈");
    }
}
