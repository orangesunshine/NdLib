package com.orange.lib.component.toast;

/**
 * @Author: orange
 * @CreateDate: 2019/9/26 8:51
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class ToastWithoutNotification extends AbsToast {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private View mView;
    private WindowManager mWM;

    private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

    private static final Utils.OnActivityDestroyedListener LISTENER =
            new Utils.OnActivityDestroyedListener() {
                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (iToast == null) return;
                    iToast.cancel();
                }
            };

    ToastWithoutNotification(Toast toast) {
        super(toast);
    }

    @Override
    public void show() {
        mView = mToast.getView();
        if (mView == null) return;
        final Context context = mToast.getView().getContext();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            Context topActivityOrApp = Utils.getTopActivityOrApp();
            if (!(topActivityOrApp instanceof Activity)) {
                Log.e("ToastUtils", "Couldn't get top Activity.");
                return;
            }
            Activity topActivity = (Activity) topActivityOrApp;
            if (topActivity.isFinishing() || topActivity.isDestroyed()) {
                Log.e("ToastUtils", topActivity + " is useless");
                return;
            }
            mWM = topActivity.getWindowManager();
            mParams.type = WindowManager.LayoutParams.LAST_APPLICATION_WINDOW;
            Utils.getActivityLifecycle().addOnActivityDestroyedListener(topActivity, LISTENER);
        } else {
            mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mParams.type = WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW + 37;
        }

        final Configuration config = context.getResources().getConfiguration();
        final int gravity = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                ? Gravity.getAbsoluteGravity(mToast.getGravity(), config.getLayoutDirection())
                : mToast.getGravity();

        mParams.y = mToast.getYOffset();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = android.R.style.Animation_Toast;

        mParams.setTitle("ToastWithoutNotification");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mParams.gravity = gravity;
        if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
            mParams.horizontalWeight = 1.0f;
        }
        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
            mParams.verticalWeight = 1.0f;
        }
        mParams.x = mToast.getXOffset();
        mParams.packageName = Utils.getApp().getPackageName();

        try {
            if (mWM != null) {
                mWM.addView(mView, mParams);
            }
        } catch (Exception ignored) { /**/ }

        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                cancel();
            }
        }, mToast.getDuration() == Toast.LENGTH_SHORT ? 2000 : 3500);
    }

    @Override
    public void cancel() {
        try {
            if (mWM != null) {
                mWM.removeViewImmediate(mView);
            }
        } catch (Exception ignored) { /**/ }
        mView = null;
        mWM = null;
        mToast = null;
    }
}
