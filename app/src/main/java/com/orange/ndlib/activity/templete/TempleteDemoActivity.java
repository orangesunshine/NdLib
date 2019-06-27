package com.orange.ndlib.activity.templete;

import android.widget.Toast;

import com.orange.lib.activity.templete.TempleteActivity;
import com.orange.lib.component.actbar.IActionBarCallback;
import com.orange.lib.component.pagestatus.IPageStatus;
import com.orange.lib.component.pagestatus.LoadingPageStatus;
import com.orange.lib.utils.ActivityUtils;
import com.orange.ndlib.R;
import com.orange.ndlib.activity.base.BaseActivityLoadingDemo;
import com.orange.ndlib.activity.base.BaseActivitySwipePullDemo;

import java.util.Random;

public class TempleteDemoActivity extends TempleteActivity {
    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_templete_demo_content;
    }

    /**
     * 获取模板布局文件
     *
     * @return
     */
    @Override
    protected int getTempleteLayoutId() {
        return R.layout.template_activity_actbar;
    }

    /**
     * 创建pagestatus
     *
     * @return
     */
    @Override
    protected IPageStatus createPageStatus() {
        return new LoadingPageStatus(mHolder);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(v -> {
            switch (v.getId()) {
                case R.id.btn_content:
                    mPageStatus.showContent();
                    break;
                case R.id.btn_loading:
                    mPageStatus.showLoading();
                    break;
                case R.id.btn_empty:
                    mPageStatus.showEmpty();
                    break;
                case R.id.btn_error:
                    mPageStatus.showError();
                    break;
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
                    ActivityUtils.launchActivity(mActivity, BaseActivityLoadingDemo.class);
                    break;
                case R.id.btn_net_pull:
                    ActivityUtils.launchActivity(mActivity, BaseActivitySwipePullDemo.class);
                    break;
            }
        }, R.id.btn_content, R.id.btn_loading, R.id.btn_empty, R.id.btn_error, R.id.btn_actbar, R.id.btn_net_loading, R.id.btn_net_pull);
        mActbar.setActionBarCallback(new IActionBarCallback() {
            @Override
            public void onLeft() {
                Toast.makeText(mActivity, "onLeft", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCenter() {
                Toast.makeText(mActivity, "onCenter", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRight() {
                Toast.makeText(mActivity, "onRight", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
