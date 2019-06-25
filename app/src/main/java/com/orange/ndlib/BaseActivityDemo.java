package com.orange.ndlib;

import android.view.View;
import android.widget.Toast;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.activity.Retry;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.actbar.IActionBarCallback;

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
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
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
                }
            }
        }, R.id.btn_content, R.id.btn_loading, R.id.btn_empty, R.id.btn_error, R.id.btn_actbar);
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

    @Retry
    void test(){
        Toast.makeText(mActivity, "retry", Toast.LENGTH_SHORT).show();
    }
}
