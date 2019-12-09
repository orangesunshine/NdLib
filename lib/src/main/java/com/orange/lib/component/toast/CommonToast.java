package com.orange.lib.component.toast;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.orange.lib.common.config.Config;
import com.orange.lib.common.convert.IHolderConvert;
import com.orange.lib.common.holder.CommonHolder;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.utils.Preconditions;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.orange.lib.constance.IConst.CUSTOM_DURATION_TOAST_DEFAULT;
import static com.orange.lib.constance.IConst.CUSTOM_DURATION_TOAST_LONG;

/**
 * @Author: orange
 * @CreateDate: 2019/9/6 13:37
 */
public class CommonToast<T> {
    private volatile static CommonToast mInstance;
    private int mLayoutId;
    private IHolder mHolder;
    public AtomicBoolean isCancel = new AtomicBoolean(true);
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private IToast mIToast;

    public static <T> CommonToast getInstance(int layoutId, T data, IHolderConvert<T> convert) {
        return getInstance(Utils.getTopActivityOrApp(), layoutId, data, convert);
    }

    public static <T> CommonToast getInstance(Context context, int layoutId, T data, IHolderConvert<T> convert) {
        if (Preconditions.isNull(context))
            context = Utils.getTopActivityOrApp();
        if (isNull()) {
            mInstance = new CommonToast(context, layoutId, data, convert);
        } else {
            mInstance.convertContent(context, layoutId, data, convert);
        }
        return mInstance;
    }

    private static boolean isNull() {
        return null == mInstance || null == mInstance.mIToast || null == mInstance.mIToast.getToast();
    }

    public IToast getIToast() {
        return mIToast;
    }

    /**
     * 点击屏幕取消toast
     */
    public static void cancelToasts4Touch() {
        if (null != mInstance)
            mInstance.cancel();
    }

    /**
     * 构造方法
     *
     * @param context
     * @param layoutId
     * @param data
     * @param convert
     */
    public CommonToast(Context context, int layoutId, T data, IHolderConvert<T> convert) {
        Toast toast = new Toast(context);
        toast.setGravity(17, 0, 0);
        mIToast = new ToastWithoutNotification(toast);
        convertContent(context, layoutId, data, convert);
    }

    /**
     * 绑定contentView
     *
     * @param context
     * @param layoutId
     * @param data
     * @param convert
     */
    private void convertContent(Context context, int layoutId, T data, IHolderConvert<T> convert) {
        if (Preconditions.isNull(context))
            context = Utils.getTopActivityOrApp();
        if (Preconditions.isNull(context) || Preconditions.isNull(convert) || layoutId <= 0)
            return;
        mLayoutId = layoutId;
        View contentView = LayoutInflater.from(context).inflate(layoutId, null);
        if (!Preconditions.isNull(contentView)) {
            mHolder = new CommonHolder(contentView);
            convert.convert(mHolder, data);
        }
        if (!Preconditions.isNull(mIToast))
            mIToast.setView(contentView);
    }

    /**
     * 重载方法
     */
    public void show() {
        show(getDuration(mIToast));
    }

    /**
     * 重载方法
     */
    public void show(int duration) {
        show(Utils.getTopActivityOrApp(), duration);
    }

    /**
     * 重载方法
     */
    public void show(Context context) {
        show(context, getDuration(mIToast));
    }

    /**
     * 显示吐司
     */
    public void show(Context context, int duration) {
        if (!Preconditions.isNull(mIToast)) {
            HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    mIToast.show(context, duration);
                }
            });
        }
    }

    /**
     * 关闭吐司
     */
    public void dismiss() {
        if (!isCancel.get() && null != mIToast) {
            mIToast.cancel();
        }
    }

    /**
     * toggle toast
     */
    public void toggleToast(Activity activity) {
        if (isCancel.get()) {
            show(activity);
        } else {
            dismiss();
        }
    }

    /**
     * toggle toast
     */
    public void toggleToast(Activity activity, int duration) {
        if (isCancel.get()) {
            show(activity, duration);
        } else {
            dismiss();
        }
    }

    /**
     * 根据点击区域控制toast显示
     *
     * @param target
     * @param event
     */
    public void targetToast(Activity activity, View target, MotionEvent event) {
        if (null == target || null == event) return;
        int[] loc = new int[2];
        target.getLocationOnScreen(loc);
        if (new RectF(loc[0], loc[1], loc[0] + target.getWidth(), loc[1] + target.getHeight()).contains(event.getRawX(), event.getRawY())) {
            toggleToast(activity);
        } else {
            dismiss();
        }
    }

    /**
     * 根据点击区域控制toast显示
     *
     * @param target
     * @param event
     */
    public void targetToast(Activity activity, int duration, View target, MotionEvent event) {
        if (null == target || null == event) return;
        int[] loc = new int[2];
        target.getLocationOnScreen(loc);
        if (new RectF(loc[0], loc[1], loc[0] + target.getWidth(), loc[1] + target.getHeight()).contains(event.getRawX(), event.getRawY())) {
            toggleToast(activity, duration);
        } else {
            dismiss();
        }
    }

    /**
     * Cancel the toast.
     */
    public void cancel() {
        if (Preconditions.isNull(mIToast)) return;
        mIToast.cancel();
    }

    /**
     * 兼容toast LENGTH_SHORT、LENGTH_LONG
     *
     * @param iToast
     * @return
     */
    private int getDuration(IToast iToast) {
        if (Preconditions.isNull(iToast)) return CUSTOM_DURATION_TOAST_DEFAULT;
        Toast toast = iToast.getToast();
        return getDuration(toast);
    }

    /**
     * 兼容toast LENGTH_SHORT、LENGTH_LONG
     *
     * @param toast
     * @return
     */
    private int getDuration(Toast toast) {
        if (Preconditions.isNull(toast)) return CUSTOM_DURATION_TOAST_DEFAULT;
        int duration = toast.getDuration();
        if (Toast.LENGTH_LONG == duration) {
            duration = CUSTOM_DURATION_TOAST_LONG;
        } else if (Toast.LENGTH_SHORT == duration) {
            duration = CUSTOM_DURATION_TOAST_DEFAULT;
        }
        return duration;
    }
    ///////////////////////////////////////////////////////////////////////////
    // base
    ///////////////////////////////////////////////////////////////////////////

    class ToastWithoutNotification extends AbsToast implements Runnable {
        private View mView;
        private WindowManager mWM;

        private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        private final OnActivityDestroyListener LISTENER =
                new OnActivityDestroyListener() {
                    @Override
                    public void onActivityDestroy() {
                        if (mInstance == null || null == mInstance.getIToast()) return;
                        mInstance.getIToast().cancel();
                    }
                };

        ToastWithoutNotification(Toast toast) {
            super(toast);
        }

        /**
         * 显示吐司
         *
         * @param context
         * @param duration
         */
        @Override
        public void show(Context context, int duration) {
            if (Preconditions.isNull(mToast)) return;
            mView = mToast.getView();
            if (Preconditions.isNull(mView)) return;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                if (Preconditions.isNull(context) || !(context instanceof Activity))
                    context = Utils.getTopActivityOrApp();
                if (Preconditions.isNull(context) || !(context instanceof Activity)) {
                    Log.e("ToastUtils", "Couldn't get top Activity.");
                    return;
                }
                Activity activity = (Activity) context;
                if (activity.isFinishing() || activity.isDestroyed()) {
                    Config.getInstance().getLog().d("CommonToast", activity + " is useless");
                    return;
                }
                mWM = activity.getWindowManager();
                mParams.type = WindowManager.LayoutParams.LAST_APPLICATION_WINDOW;
                Utils.getActivityLifecycle().addActDesLr(activity, LISTENER);
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
            mParams.packageName = context.getPackageName();

            HANDLER.removeCallbacksAndMessages(null);

            addView();

            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hide();
                }
            }, duration);
        }

        private void addView() {
            if (isCancel.compareAndSet(true, false)) {
                try {
                    if (!Preconditions.isNull(mWM)) {
                        mWM.addView(mView, mParams);
                    }
                } catch (Exception ignored) { /**/ }
            }
        }

        @Override
        public void setView(View view) {
            super.setView(view);
            if (!isCancel.get()) {
                try {
                    if (!Preconditions.isNull(mWM)) {
                        mWM.removeViewImmediate(mView);
                        mWM.addView(view, mParams);
                        mView = view;
                    }
                } catch (Exception ignored) { /**/ }
            }
        }

        public void hide() {
            if (isCancel.compareAndSet(false, true)) {
                try {
                    if (!Preconditions.isNull(mWM)) {
                        mWM.removeViewImmediate(mView);
                    }
                } catch (Exception ignored) { /**/ }
            }
        }

        @Override
        public void cancel() {
            try {
                if (!Preconditions.isNull(mWM)) {
                    mWM.removeViewImmediate(mView);
                }
            } catch (Exception ignored) { /**/ }
            mView = null;
            mWM = null;
            mToast = null;
            mInstance = null;
        }

        @Override
        public void run() {

        }
    }
}
