package com.orange.lib.utils;

import android.content.Context;
import android.content.Intent;

import androidx.core.util.Preconditions;

import com.orange.lib.mvp.view.activity.base.BaseActivity;


public class ActivityUtils {
    public static void launchActivity(Context context, Class<? extends BaseActivity> clazz) {
        Preconditions.checkNotNull(context);
        context.startActivity(new Intent(context, clazz));
    }
}
