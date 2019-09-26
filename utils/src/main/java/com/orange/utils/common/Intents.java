package com.orange.utils.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

/**
 * @ProjectName: NdLib
 * @Package: com.orange.utils.common
 * @ClassName: Intents
 * @Description:
 * @Author: orange
 * @CreateDate: 2019/7/31 9:29
 * @UpdateUser:
 * @UpdateDate: 2019/7/31 9:29
 * @UpdateRemark:
 * @Version: 1.0
 */
public class Intents {

    /**
     * 生成页面过场动画bundle，sdk大于等于16
     *
     * @param context
     * @param enterAnim
     * @param exit
     * @return
     */
    public static Bundle makeCustomAnimation(Context context, int enterAnim, int exit) {
        if (null == context) return null;
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exit).toBundle();
    }

    /**
     * make bundle with scene transition animation
     *
     * @param activity
     * @param sharedElements
     * @return
     */
    private static Bundle getOptionsBundle(final Activity activity,
                                           final View[] sharedElements) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return null;
        if (sharedElements == null) return null;
        int len = sharedElements.length;
        if (len <= 0) return null;
        @SuppressWarnings("unchecked")
        Pair<View, String>[] pairs = new Pair[len];
        for (int i = 0; i < len; i++) {
            pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
    }

    /**
     * 生成过场动画bundle，sdk大于等于16activity跳转
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean intentAvailable(Context context, Intent intent) {
        if (null == context || null == intent) return false;
        PackageManager pm = context.getPackageManager();
        return pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}
