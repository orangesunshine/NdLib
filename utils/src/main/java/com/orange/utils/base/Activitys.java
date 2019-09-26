package com.orange.utils.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.orange.utils.common.Intents;
import com.orange.utils.common.Texts;

/**
 * @ProjectName: NdLib
 * @Package: com.orange.utils.base
 * @ClassName: Activitys
 * @Description:
 * @Author: orange
 * @CreateDate: 2019/7/31 9:20
 * @UpdateUser:
 * @UpdateDate: 2019/7/31 9:20
 * @UpdateRemark:
 * @Version: 1.0
 */
public class Activitys {

// <editor-fold defaultstate="collapsed" desc="判断activity存在">

    /**
     * 判断activity存在
     *
     * @param context
     * @param clazz
     * @return
     */
    public static boolean isActivityExist(Context context, Class<Activity> clazz) {
        if (null == context || null == clazz) return false;
        Intent intent = new Intent(context, clazz);
        PackageManager pm = context.getPackageManager();
        return !(null == pm.resolveActivity(intent, 0) || null == intent.resolveActivity(pm) || 0 == pm.queryIntentActivities(intent, 0).size());
    }

    /**
     * 判断activity存在
     *
     * @param context
     * @param pkgname
     * @param clazzname
     * @return
     */
    public static boolean isActivityExist(Context context, String pkgname, String clazzname) {
        if (null == context || Texts.isSpace(pkgname) || Texts.isSpace(clazzname)) return false;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(pkgname, clazzname));
        PackageManager pm = context.getPackageManager();
        return !(null == pm.resolveActivity(intent, 0) || null == intent.resolveActivity(pm) || 0 == pm.queryIntentActivities(intent, 0).size());
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="view获取activity">

    /**
     * view获取activity
     *
     * @param view
     * @return
     */
    public static Activity getActivityByView(View view) {
        if (null == view) return null;
        return getActivityByContext(view.getContext());
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="context获取activity">

    /**
     * context获取activity
     *
     * @param context
     * @return
     */
    public static Activity getActivityByContext(Context context) {
        if (null == context) return null;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) return (Activity) context;
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="activity跳转">

    /**
     * activity跳转过场动画，sdk大于等于16 方法
     *
     * @param context
     * @param intent
     * @param extra
     * @param opt     activity跳转过场动画opt
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean launchActivity(Context context, Intent intent, Bundle extra, Bundle opt) {
        if (null == context) return false;
        if (null != extra)
            intent.putExtras(extra);
        if (!(context instanceof Activity))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!Intents.intentAvailable(context, intent)) return false;
        context.startActivity(intent, opt);
        return true;
    }

    /**
     * activity跳转，捆绑参数extra
     *
     * @param context
     * @param pkgname
     * @param clazzname
     */
    public static boolean launchActivity(Context context, String pkgname, String clazzname) {
        return launchActivity(context, pkgname, clazzname, null);
    }

    /**
     * activity跳转，捆绑参数extra
     *
     * @param context
     * @param pkgname
     * @param clazzname
     * @param extra     参数extra
     */
    public static boolean launchActivity(Context context, String pkgname, String clazzname, Bundle extra) {
        if (Texts.isSpace(pkgname) || Texts.isSpace(clazzname)) return false;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setComponent(new ComponentName(pkgname, clazzname));
        return launchActivity(context, intent, extra);
    }

    /**
     * activity跳转，捆绑参数extra
     *
     * @param context
     * @param intent
     * @param extra   参数extra
     */
    public static boolean launchActivity(Context context, Intent intent, Bundle extra) {
        if (null == intent) return false;
        if (null != extra)
            intent.putExtras(extra);
        return launchActivity(context, intent);
    }

    /**
     * activity跳转
     *
     * @param context
     * @param intent
     */
    public static boolean launchActivity(Context context, Intent intent) {
        if (null == context) return false;
        if (!(context instanceof Activity))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!Intents.intentAvailable(context, intent)) return false;
        context.startActivity(intent);
        return true;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="activity跳转，兼容sdk">

    /**
     * activity跳转过场动画，兼容sdk
     *
     * @param context
     * @param pkgname
     * @param clazzname
     * @param extra
     * @param enterAnim
     * @param exitAnim
     */
    public static boolean compatLaunchActivity(Context context, String pkgname, String clazzname, Bundle extra, int enterAnim, int exitAnim) {
        if (Texts.isSpace(pkgname) || Texts.isSpace(clazzname)) return false;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setComponent(new ComponentName(pkgname, clazzname));
        return compatLaunchActivity(context, intent, extra, enterAnim, exitAnim);
    }

    /**
     * activity跳转过场动画，兼容sdk
     *
     * @param context
     * @param intent
     * @param extra
     * @param enterAnim
     * @param exitAnim
     */
    public static boolean compatLaunchActivity(Context context, Intent intent, Bundle extra, int enterAnim, int exitAnim) {
        if (null == context || null == intent) return false;
        if (null != extra)
            intent.putExtras(extra);
        if (!(context instanceof Activity))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!Intents.intentAvailable(context, intent)) return false;
        if (0 == enterAnim || 0 == exitAnim) {
            context.startActivity(intent);
            return true;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent);
            if (context instanceof Activity)
                ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        } else {
            context.startActivity(intent, Intents.makeCustomAnimation(context, enterAnim, exitAnim));
        }
        return true;
    }

    /**
     * activity跳转过场动画，兼容sdk
     *
     * @param context
     * @param intent
     * @param extra
     * @param enterAnim
     * @param exitAnim
     */
    public static boolean compatLaunchActivity(Context context, Intent intent, Bundle extra, int enterAnim, int exitAnim, int requestCode) {
        if (null == context || null == intent) return false;
        if (null != extra)
            intent.putExtras(extra);
        if (!(context instanceof Activity))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return startActivityOrForResult(context, intent, enterAnim, exitAnim, requestCode);
    }

    public static boolean startActivityOrForResult(Context context, Intent intent, int enterAnim, int exitAnim, int requestCode) {
        if (!Intents.intentAvailable(context, intent)) return false;
        if (0 == enterAnim || 0 == exitAnim) {
            if (-1 != requestCode && context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent);
            }
            return true;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            if (-1 != requestCode && context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent);
            }
            if (context instanceof Activity)
                ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        } else {
            if (-1 != requestCode && context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent, Intents.makeCustomAnimation(context, enterAnim, exitAnim));
            }
        }
        return true;
    }
// </editor-fold>
}
