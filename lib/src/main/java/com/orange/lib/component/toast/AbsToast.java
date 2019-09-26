package com.orange.lib.component.toast;

import android.view.View;
import android.widget.Toast;

/**
 * @Author: orange
 * @CreateDate: 2019/9/26 8:50
 */
public abstract class AbsToast implements IToast {

    Toast mToast;

    AbsToast(Toast toast) {
        mToast = toast;
    }

    @Override
    public void setView(View view) {
        mToast.setView(view);
    }

    @Override
    public View getView() {
        return mToast.getView();
    }

    @Override
    public void setDuration(int duration) {
        mToast.setDuration(duration);
    }

    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        mToast.setGravity(gravity, xOffset, yOffset);
    }

    @Override
    public void setText(int resId) {
        mToast.setText(resId);
    }

    @Override
    public void setText(CharSequence s) {
        mToast.setText(s);
    }
}
