package com.orange.ndlib.activity.base;

import com.orange.lib.component.actbar.IActionBarCallback;
import com.orange.lib.mvp.view.activity.base.BaseActivity;
import com.orange.lib.utils.Activitys;
import com.orange.lib.utils.toast.Toasts;
import com.orange.ndlib.R;

import java.util.Random;

public class BaseActivityDemo extends BaseActivity {
    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_base_demo;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(v -> {
            switch (v.getId()) {
                case R.id.btn_actbar:
                    int i = new Random().nextInt(5);
                    switch (i) {
                        case 0:
                            mActbar.setLeftText("left");
                            break;
                        case 1:
                            mActbar.setLeftImg(R.drawable.ic_photo_gray);
                            break;
                        case 2:
                            mActbar.setRightText("right");
                            break;
                        case 3:
                            mActbar.setRightImg(R.drawable.ic_image_white);
                            break;
                        case 4:
                            mActbar.setTitle("BaseActivityDemo");
                            break;
                    }
                    break;
                case R.id.btn_net_loading:
                    Activitys.launchActivity(mActivity, BaseActivityLoadingDemo.class);
                    break;
                case R.id.btn_net_pull:
                    Activitys.launchActivity(mActivity, BaseActivitySwipePullDemo.class);
                    break;
                case R.id.btn_net_pull_page:
                    Activitys.launchActivity(mActivity, BaseActivitySwipePullPageDemo.class);
                    break;
            }
        }, R.id.btn_content, R.id.btn_loading, R.id.btn_empty, R.id.btn_error, R.id.btn_actbar, R.id.btn_net_loading, R.id.btn_net_pull, R.id.btn_net_pull_page);
        mActbar.setActionBarCallback(new IActionBarCallback() {
            @Override
            public void onLeft() {
                Toasts.showMsg("onLeft", 5000);
            }

            @Override
            public void onTitle() {
                Toasts.showMsg("onTitle", 10000);
            }

            @Override
            public void onRight() {
                Toasts.showMsg("onRight", 1000);
            }
        });
    }
}
