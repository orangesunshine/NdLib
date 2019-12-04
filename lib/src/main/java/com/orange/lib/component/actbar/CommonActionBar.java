package com.orange.lib.component.actbar;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;

/**
 * 通用标题栏
 */
public class CommonActionBar implements IActionBar {
    //vars
    private IHolder mHolder;
    private IActionBarCallback mActionBarCallback;

    /**
     * 构造方法
     *
     * @param holder
     */
    public CommonActionBar(IHolder holder) {
        mHolder = holder;
    }

    /**
     * 构造方法
     *
     * @param holder
     */
    public CommonActionBar(IHolder holder, IActionBarCallback actionBarCallback) {
        this(holder);
        setActionBarCallback(actionBarCallback);
    }

    @Override
    public void setActionBarCallback(IActionBarCallback actionBarCallback) {
        mActionBarCallback = actionBarCallback;
        mHolder.addOnItemChildClick(view -> {
            int i = view.getId();
            if (i == R.id.id_fl_left_actbar_orange) {
                if (null != mActionBarCallback)
                    mActionBarCallback.onLeft();
            } else if (i == R.id.id_fl_title_actbar_orange) {
                if (null != mActionBarCallback)
                    mActionBarCallback.onTitle();
            } else if (i == R.id.id_fl_right_actbar_orange) {
                if (null != mActionBarCallback)
                    mActionBarCallback.onRight();
            }
        }, R.id.id_fl_left_actbar_orange, R.id.id_fl_right_actbar_orange, R.id.id_fl_title_actbar_orange);
    }

    @Override
    public void setLeftImg(int imgResId) {
        if (null != mHolder) {
            mHolder.setVisible(R.id.id_iv_left_actbar_orange, true);
            mHolder.setVisible(R.id.id_tv_left_actbar_orange, false);
            mHolder.setImageResource(R.id.id_iv_left_actbar_orange, imgResId);
        }
    }

    @Override
    public void setLeftText(String leftText) {
        if (null != mHolder) {
            mHolder.setVisible(R.id.id_iv_left_actbar_orange, false);
            mHolder.setVisible(R.id.id_tv_left_actbar_orange, true);
            mHolder.setText(R.id.id_tv_left_actbar_orange, leftText);
        }
    }

    @Override
    public void setTitle(String title) {
        if (null != mHolder)
            mHolder.setText(R.id.id_tv_title_actbar_orange, title);
    }

    @Override
    public void setRightImg(int imgResId) {
        if (null != mHolder) {
            mHolder.setVisible(R.id.id_iv_right_actbar_orange, true);
            mHolder.setVisible(R.id.id_tv_right_actbar_orange, false);
            mHolder.setImageResource(R.id.id_iv_right_actbar_orange, imgResId);
        }
    }

    @Override
    public void setRightText(String rightText) {
        if (null != mHolder) {
            mHolder.setVisible(R.id.id_iv_right_actbar_orange, false);
            mHolder.setVisible(R.id.id_tv_right_actbar_orange, true);
            mHolder.setText(R.id.id_tv_right_actbar_orange, rightText);
        }
    }
}
