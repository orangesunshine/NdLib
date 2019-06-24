package com.orange.ndlib;

import android.view.View;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.activity.templete.TempleteActivity;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.utils.ActivityUtils;

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
        mHolder.setOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_base:
                        ActivityUtils.launchActivity(mActivity, BaseActivityDemo.class);
                        break;
                    case R.id.btn_templete:
                        ActivityUtils.launchActivity(mActivity, TempleteActivity.class);
                        break;
                }
            }
        }, R.id.btn_base, R.id.btn_templete);
    }
}
