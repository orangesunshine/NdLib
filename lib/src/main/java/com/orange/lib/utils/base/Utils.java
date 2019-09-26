package com.orange.lib.utils.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.orange.lib.utils.log.Logs;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/9/26 9:12
 */
public class Utils {
    //全局 application
    private static Application sApplication;

    /**
     * act生命周期：前后台切换，act销毁回调，软键盘内存泄漏
     */
    private static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();

    private Utils() {
    }

    public static void init(Context context) {
        if (Preconditions.isNull(context)) {
            init(getAppByReflect());
            return;
        }
        init((Application) context.getApplicationContext());
    }

    /**
     * 初始化：设置全局上下文，设置生命周期回调
     *
     * @param app
     */
    public static void init(Application app) {
        if (!Preconditions.isNull(app)) {
            if (Preconditions.isNull(sApplication)) {
                sApplication = app;
            } else if (sApplication.getClass() != app.getClass()) {
                sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
                ACTIVITY_LIFECYCLE.clearActStack();
                sApplication = app;
                sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            }
        } else if (Preconditions.isNull(sApplication)) {
            sApplication = getAppByReflect();
            sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        }
    }

    /**
     * 获取可用application
     *
     * @return
     */
    public static Application getApp() {
        if (!Preconditions.isNull(sApplication)) return sApplication;
        Application app = getAppByReflect();
        init(app);
        return app;
    }

    /**
     * 获取activity生命周期
     *
     * @return
     */
    public static Application.ActivityLifecycleCallbacks getActivityLifecycle() {
        return ACTIVITY_LIFECYCLE;
    }

    /**
     * 获取activity栈内全部activity
     *
     * @return
     */
    public static LinkedList<Activity> getActivityList() {
        return ACTIVITY_LIFECYCLE.getActivityList();
    }

    /**
     * 获取栈顶activity或application
     *
     * @return
     */
    static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topAct = ACTIVITY_LIFECYCLE.getTopAct();
            return Preconditions.isNull(topAct) ? getApp() : topAct;
        }
        return getApp();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 工具方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 反射获取当前application
     *
     * @return
     */
    private static Application getAppByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) Logs.logc("u should init first");
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断当前应用是否前台显示
     *
     * @return
     */
    private static boolean isAppForeground() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getApp().getPackageName());
            }
        }
        return false;
    }
}
