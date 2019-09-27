package com.orange.lib.component.toast;

import android.view.View;
import android.widget.Toast;

import com.orange.lib.utils.base.Preconditions;

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
        if (!Preconditions.isNull(mToast))
            mToast.setView(view);
    }

    @Override
    public View getView() {
        if (!Preconditions.isNull(mToast))
            return mToast.getView();
        return null;
    }

    @Override
    public void setDuration(int duration) {
        if (!Preconditions.isNull(mToast))
            mToast.setDuration(duration);
    }

    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (!Preconditions.isNull(mToast))
            mToast.setGravity(gravity, xOffset, yOffset);
    }

    @Override
    public Toast getToast() {
        return mToast;
    }
}
