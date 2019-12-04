package com.orange.lib.utils.antishake;

import android.os.SystemClock;
import android.view.View;

import com.orange.lib.utils.base.Preconditions;
import com.orange.lib.utils.log.Logs;

/**
 * @Author: orange
 * @CreateDate: 2019/9/17 10:00
 */
public final class SingleClickUtils {
    private static final long DEFAULT_MIN_INTERVAL = 10;//防止方法递归调用
    public static final long DEFAULT_INTERVAL_MILLS = 200;//默认抖动间隔200毫秒
    public static final long MIDDLE_INTERVAL_MILLS = 500;
    public static final long MAX_INTERVAL_MILLS = 1500;
    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;

    /**
     * 重载是否是快速点击
     *
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(DEFAULT_INTERVAL_MILLS);
    }

    /**
     * 是否是快速点击
     *
     * @param intervalMillis 时间间期（毫秒）
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(long intervalMillis) {
        long time = SystemClock.elapsedRealtime();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval > DEFAULT_MIN_INTERVAL && timeInterval < intervalMillis) {
            Logs.i("SingleClickUtils.isFastDoubleClick.true： " + intervalMillis);
            return true;
        } else {
            mLastClickTime = time;
            Logs.i("SingleClickUtils.isFastDoubleClick.false： " + intervalMillis);
            return false;
        }
    }

    /**
     * 最近一次点击的控件ID
     */
    private static int mLastClickViewId;

    /**
     * 是否是快速点击
     *
     * @param view           点击的控件
     * @param intervalMillis 时间间期（毫秒）
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(View view, long intervalMillis) {
        return isFastDoubleClick(view, intervalMillis, false);
    }

    /**
     * 是否是快速点击
     *
     * @param view           点击的控件
     * @param intervalMillis 时间间期（毫秒）
     * @param supportsame    是否同一个控件快速点击
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(View view, long intervalMillis, boolean supportsame) {
        if (Preconditions.isNull(view)) return true;
        int viewId = view.getId();
        long time = SystemClock.elapsedRealtime();
        long timeInterval = time - mLastClickTime;
        boolean fastClick = timeInterval > DEFAULT_MIN_INTERVAL && timeInterval < intervalMillis;
        if (supportsame)
            fastClick &= viewId == mLastClickViewId;

        if (fastClick) {
            Logs.i("SingleClickUtils.isFastDoubleClick.true： " + intervalMillis);
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            Logs.i("SingleClickUtils.isFastDoubleClick.false： " + intervalMillis);
        }
        return fastClick;
    }
}
