package com.orange.lib.component.toast;

import android.view.View;

import androidx.annotation.StringRes;

public interface IToast {
    void show();

    void cancel();

    void setView(View view);

    View getView();

    void setDuration(int duration);

    void setGravity(int gravity, int xOffset, int yOffset);

    void setText(@StringRes int resId);

    void setText(CharSequence charSequence);
}
