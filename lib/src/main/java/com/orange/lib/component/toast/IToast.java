package com.orange.lib.component.toast;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public interface IToast {
    void show(Context context, int duration);

    void cancel();

    void setView(View view);

    View getView();

    void setDuration(int duration);

    void setGravity(int gravity, int xOffset, int yOffset);

    Toast getToast();
}
