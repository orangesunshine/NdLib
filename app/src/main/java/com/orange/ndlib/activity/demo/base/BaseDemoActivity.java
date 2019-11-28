package com.orange.ndlib.activity.demo.base;

import com.orange.lib.component.actbar.IActionBarCallback;
import com.orange.lib.mvp.view.activity.base.BaseActivity;
import com.orange.lib.utils.toast.Toasts;
import com.orange.ndlib.R;

import java.util.Random;

public class BaseDemoActivity extends BaseActivity {
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
                            mActbar.setTitle("BaseDemoActivity");
                            break;
                    }
                    break;
            }
        }, R.id.btn_actbar);
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
