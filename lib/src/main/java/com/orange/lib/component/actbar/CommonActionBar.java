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
            if (i == R.id.fl_left_actbar) {
                if (null != mActionBarCallback)
                    mActionBarCallback.onLeft();
            } else if (i == R.id.fl_title_actbar) {
                if (null != mActionBarCallback)
                    mActionBarCallback.onTitle();
            } else if (i == R.id.fl_right_actbar) {
                if (null != mActionBarCallback)
                    mActionBarCallback.onRight();
            }
        }, R.id.fl_left_actbar, R.id.fl_right_actbar, R.id.fl_title_actbar);
    }

    @Override
    public void setLeftImg(int imgResId) {
        if (null != mHolder) {
            mHolder.setVisible(R.id.iv_left_actbar, true);
            mHolder.setVisible(R.id.tv_left_actbar, false);
            mHolder.setImageResource(R.id.iv_left_actbar, imgResId);
        }
    }

    @Override
    public void setLeftText(String leftText) {
        if (null != mHolder) {
            mHolder.setVisible(R.id.iv_left_actbar, false);
            mHolder.setVisible(R.id.tv_left_actbar, true);
            mHolder.setText(R.id.tv_left_actbar, leftText);
        }
    }

    @Override
    public void setTitle(String title) {
        if (null != mHolder)
            mHolder.setText(R.id.tv_title_actbar, title);
    }

    @Override
    public void setRightImg(int imgResId) {
        if (null != mHolder) {
            mHolder.setVisible(R.id.iv_right_actbar, true);
            mHolder.setVisible(R.id.tv_right_actbar, false);
            mHolder.setImageResource(R.id.iv_right_actbar, imgResId);
        }
    }

    @Override
    public void setRightText(String rightText) {
        if (null != mHolder) {
            mHolder.setVisible(R.id.iv_right_actbar, false);
            mHolder.setVisible(R.id.tv_right_actbar, true);
            mHolder.setText(R.id.tv_right_actbar, rightText);
        }
    }
}
