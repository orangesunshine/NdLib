package com.orange.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: NdLib
 * @Package: com.orange.lib.utils
 * @ClassName: Utils
 * @Description:
 * @Author: orange
 * @CreateDate: 2019/7/10 9:36
 * @UpdateUser:
 * @UpdateDate: 2019/7/10 9:36
 * @UpdateRemark:
 * @Version: 1.0
 */
public class Utils {
    public static Application sApp;
    private static final ActivityLifeCycleImpl ACTIVITY_LIFECYCLE = new ActivityLifeCycleImpl();

    private static List<String> mExcludeActivityNames = new ArrayList<>();

    private Utils() {
        //no instance
    }

    public static Application getApp() {
        if (null == sApp)
            init(getApplicationByReflect());
        return sApp;
    }

    public static void init(Context context) {
        if (null == context) {
            init(getApplicationByReflect());
        } else {
            init((Application) context.getApplicationContext());
        }
    }

    /**
     * init Utils
     *
     * @param app
     */
    public static void init(Application app) {
        if (null == sApp) {
            if (null == app) {
                sApp = getApplicationByReflect();
            } else {
                sApp = app;
            }
            sApp.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        } else {
            if (null != app && app.getClass() != sApp.getClass()) {
                sApp.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
                ACTIVITY_LIFECYCLE.clearActivityList();

                sApp = app;
                sApp.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            }
        }
    }

    static ActivityLifeCycleImpl getActivityLifecycle() {
        return ACTIVITY_LIFECYCLE;
    }

    static LinkedList<Activity> getActivityList() {
        return ACTIVITY_LIFECYCLE.mActivityList;
    }

    static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topActivity = ACTIVITY_LIFECYCLE.getTopActivity();
            return topActivity == null ? Utils.getApp() : topActivity;
        } else {
            return Utils.getApp();
        }
    }

    static boolean isAppForeground() {
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

    /**
     * 反射获取application
     *
     * @return
     */
    private static Application getApplicationByReflect() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThead");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (null == app)
                throw new NullPointerException("null == app u should init first! ");
            return (Application) app;
        } catch (Exception e) {
            throw new NullPointerException("null == app u should init first! ");
        }
    }

    static class ActivityLifeCycleImpl implements Application.ActivityLifecycleCallbacks {
        private LinkedList<Activity> mActivityList = new LinkedList<>();//activity容器
        private Map<Object, OnAppStatusChangedListener> mAppStatusMap = new HashMap<>();//前后台切换
        private Map<Activity, Set<OnActivityDestroyListener>> mActivityDestroyMap = new HashMap<>();//销毁回调

        private int mConfigCount;
        private boolean mIsBackground;
        private int mForgroundCount;

        @Override

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (!mIsBackground)
                setTopActivity(activity);
            if (mConfigCount < 0) {
                mConfigCount++;
            } else {
                mForgroundCount++;
                if (mForgroundCount > 0 && mIsBackground)
                    mIsBackground = false;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
            if (mIsBackground) {
                mIsBackground = false;
                postStatusChange(false);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                mConfigCount--;
            } else {
                mForgroundCount--;
                if (mForgroundCount < 0) {
                    mIsBackground = true;
                    postStatusChange(true);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivityList.remove(activity);
            consumeActivityDestroydListener(activity);
            fixSoftInputLeaks(activity);
        }

        /**
         * 设置obj前后台监听
         *
         * @param obj
         * @param listener
         */
        public void addOnAppStatusChangedListener(Object obj, OnAppStatusChangedListener listener) {
            if (null == mAppStatusMap) return;
            mAppStatusMap.put(obj, listener);
        }

        /**
         * 删除前后台监听
         *
         * @param obj
         */
        public void removeOnAppStatusChangedListener(Object obj) {
            if (null == mAppStatusMap) return;
            mAppStatusMap.remove(obj);
        }

        /**
         * 添加activity销毁回调事件
         *
         * @param activity
         * @param listener
         */
        public void addActivityDestroyedListener(Activity activity, OnActivityDestroyListener listener) {
            if (null == mActivityDestroyMap || null == activity || null == listener) return;
            Set<OnActivityDestroyListener> listeners = mActivityDestroyMap.get(activity);
            if (null == listeners) {
                listeners = new HashSet<>();
                mActivityDestroyMap.put(activity, listeners);
            }
            if (listeners.contains(listener)) return;
            listeners.add(listener);
        }

        /**
         * 软键盘内存泄露
         *
         * @param activity
         */
        private void fixSoftInputLeaks(Activity activity) {
            if (null == activity) return;
            InputMethodManager imm = (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null == imm) return;
            String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
            for (String leakView : leakViews) {
                try {
                    Field leak = imm.getClass().getDeclaredField(leakView);
                    if (null == leak) continue;
                    if (!leak.isAccessible())
                        leak.setAccessible(true);
                    Object view = leak.get(imm);
                    if (null == view || !(view instanceof View) || activity.getWindow().getDecorView().getRootView() != ((View) view).getRootView())
                        continue;
                    leak.set(imm, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * activity销毁回调事件
         *
         * @param activity
         */
        private void consumeActivityDestroydListener(Activity activity) {
            if (null == activity || null == mActivityDestroyMap || !mActivityDestroyMap.containsKey(activity))
                return;
            Set<OnActivityDestroyListener> listeners = mActivityDestroyMap.get(activity);
            if (null == listeners && listeners.isEmpty()) return;
            for (OnActivityDestroyListener listener : listeners) {
                if (null != listener)
                    listener.onActivityDestroy(activity);
            }
            mActivityDestroyMap.remove(activity);
        }

        /**
         * 切换前后台
         *
         * @param background
         */
        public void postStatusChange(boolean background) {
            if (null != mAppStatusMap && !mAppStatusMap.isEmpty()) {
                for (OnAppStatusChangedListener listener : mAppStatusMap.values()) {
                    if (null == listener) continue;
                    listener.onStatus(background);
                }
            }
        }

        /**
         * 清空activity容器
         */
        public void clearActivityList() {
            if (null != mActivityList)
                mActivityList.clear();
        }

        /**
         * 设置顶部activity
         *
         * @param activity
         */
        public void setTopActivity(Activity activity) {
            if (null != mExcludeActivityNames && !mExcludeActivityNames.isEmpty() && mExcludeActivityNames.contains(activity.getClass().getName()))
                return;
            if (mActivityList.contains(activity)) {
                if (!mActivityList.getLast().equals(activity)) {
                    mActivityList.remove(activity);
                    mActivityList.add(activity);
                }
            } else {
                mActivityList.add(activity);
            }
        }

        /**
         * 反射获取顶部activity
         */
        public Activity getTopActivityByReflect() {
            try {
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThead");
                if (null == activityThreadClass) return null;
                Object thread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
                Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
                Map activities = (Map) mActivityListField.get(thread);
                if (null == activities) return null;
                for (Object activityRecord : activities.values()) {
                    if (null == activityRecord) continue;
                    Class<?> activityRecordClass = activityRecord.getClass();
                    Field paused = activityRecordClass.getDeclaredField("paused");
                    paused.setAccessible(true);
                    if (!paused.getBoolean(activityRecordClass)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        return (Activity) activityField.get(activityRecordClass);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 获取顶部activity
         *
         * @return
         */
        public Activity getTopActivity() {
            Activity activity = mActivityList.getLast();
            if (null != activity) return activity;
            activity = getTopActivityByReflect();
            if (null != activity)
                setTopActivity(activity);
            return activity;
        }
    }


    /**
     * 前后台切换监听
     */
    interface OnAppStatusChangedListener {
        void onStatus(boolean backgroud);
    }

    /**
     * 销毁监听
     */
    interface OnActivityDestroyListener {
        void onActivityDestroy(Activity activity);
    }
}
