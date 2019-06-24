package com.orange.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.util.Preconditions;


public class ActivityUtils {
    public static void launchActivity(Context context, Class<? extends Activity> clazz) {
        Preconditions.checkNotNull(context);
        context.startActivity(new Intent(context, clazz));
    }
}
