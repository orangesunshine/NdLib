package com.orange.thirdparty.statusbar;

import android.app.Activity;

import androidx.core.util.Preconditions;

import com.githang.statusbar.StatusBarCompat;
import com.orange.lib.component.statusbar.IStatusBar;

public class StatusBarTranslucent implements IStatusBar {
    private static final StatusBarTranslucent ourInstance = new StatusBarTranslucent();

    public static StatusBarTranslucent getInstance() {
        return ourInstance;
    }

    private StatusBarTranslucent() {
    }

    @Override
    public void setStatusBar(Activity activity) {
        Preconditions.checkNotNull(activity);
        StatusBarCompat.setTranslucent(activity.getWindow(), false);
    }
}
