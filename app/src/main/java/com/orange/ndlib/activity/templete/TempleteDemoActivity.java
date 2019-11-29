package com.orange.ndlib.activity.templete;

import android.widget.Toast;

import com.orange.lib.activity.templete.TempleteActivity;
import com.orange.lib.component.actbar.IActionBarCallback;
import com.orange.lib.mvp.view.page.loading.LoadingDialogPage;
import com.orange.lib.mvp.contact.presenter.NetPresenter;
import com.orange.lib.utils.Activitys;
import com.orange.ndlib.R;
import com.orange.ndlib.activity.demo.loading.NetDemoActivity;
import com.orange.ndlib.activity.demo.swip.SwipePullDemoActivity;

import java.util.Random;

public class TempleteDemoActivity extends TempleteActivity {
    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_templete_demo;
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
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        LoadingDialogPage pageStatus = new LoadingDialogPage(mLoading, mHolder);
        mHolder.addOnItemChildClick(v -> {
            switch (v.getId()) {
                case R.id.btn_content:
                    pageStatus.showContent();
                    break;
                case R.id.btn_loading:
                    pageStatus.showLoading();
                    break;
                case R.id.btn_error:
                    pageStatus.showError();
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
                            mActbar.setTitle("BaseDemoActivity");
                            break;
                    }
                    break;
                case R.id.btn_net_loading:
                    Activitys.launchActivity(mActivity, NetDemoActivity.class);
                    break;
                case R.id.btn_net_pull:
                    Activitys.launchActivity(mActivity, SwipePullDemoActivity.class);
                    break;
            }
        }, R.id.btn_content, R.id.btn_loading, R.id.btn_empty, R.id.btn_error, R.id.btn_actbar, R.id.btn_net_loading, R.id.btn_net_pull);
        mActbar.setActionBarCallback(new IActionBarCallback() {
            @Override
            public void onLeft() {
                Toast.makeText(mActivity, "onLeft", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTitle() {
                Toast.makeText(mActivity, "onTitle", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRight() {
                Toast.makeText(mActivity, "onRight", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected NetPresenter getPresenter() {
        return null;
    }
}
