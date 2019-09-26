package com.orange.utils.common;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;

/**
 * @ProjectName: NdLib
 * @Package: com.orange.utils.common
 * @ClassName: Sizes
 * @Description:
 * @Author: orange
 * @CreateDate: 2019/7/31 10:29
 * @UpdateUser:
 * @UpdateDate: 2019/7/31 10:29
 * @UpdateRemark:
 * @Version: 1.0
 */
public class Sizes {

    /**
     * 单位换算
     *
     * @param value
     * @param unit
     * @return 单位换算value
     */
    public static float applyDimension(float value, int unit) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return value;
    }

    /**
     * dp换算px
     *
     * @param value
     * @return
     */
    public static int dp2px(float value) {
        return (int) (value * getDensity() + 0.5f);
    }

    /**
     * px换算dp
     *
     * @param value
     * @return
     */
    public static int px2dp(float value) {
        return (int) (value / getDensity() + 0.5f);
    }

    /**
     * dp换算px
     *
     * @param value
     * @return
     */
    public static int sp2px(float value) {
        return (int) (getScaledDensity() * (value + 0.5f));
    }

    /**
     * px换算dp
     *
     * @param value
     * @return
     */
    public static int px2sp(float value) {
        return (int) (value / getScaledDensity() + 0.5f);
    }

    /**
     * 获取屏幕密度
     *
     * @return
     */
    public static float getDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * 获取font屏幕密度
     *
     * @return
     */
    public static float getScaledDensity() {
        return Resources.getSystem().getDisplayMetrics().scaledDensity;
    }

    /**
     * 获取view的宽高
     *
     * @param view
     * @return
     */
    public static int[] measureView(View view) {
        if (null == view) return null;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (null == lp)
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int widthSpec = lp.width;
        int height = lp.height;
        int heightSpec;
        if (height > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }
}
