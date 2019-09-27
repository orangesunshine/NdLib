package com.orange.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;


public class Activitys {
    public static void launchActivity(Context context, Class<? extends Activity> clazz) {
        Preconditions.checkNotNull(context);
        context.startActivity(new Intent(context, clazz));
    }

    public static Activity getActivityByView(@NonNull final View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        Log.e("BarUtils", "the view's Context is not an Activity.");
        return null;
    }
}
