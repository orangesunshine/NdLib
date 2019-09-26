package com.orange.lib.utils.base;

/**
 * @Author: orange
 * @CreateDate: 2019/9/26 9:24
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

    ///////////////////////////////////////////////////////////////////////////
    // 重写方法
    ///////////////////////////////////////////////////////////////////////////
    private int mForegroundCount;//前台act的数量
    private int mConfigCount;//manifest设置configchange，act生命周期
    private boolean mIsForeground;//是否前台

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setTopAct(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!mIsForeground)
            setTopAct(activity);
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        setTopAct(activity);
        if (!mIsForeground) {
            mIsForeground = true;
            postStatus(true);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {/**/}

    @Override
    public void onActivityStopped(Activity activity) {
        if (!Preconditions.isNull(activity) && activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                mIsForeground = false;
                postStatus(false);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityList.remove(activity);
        fixSoftInputLeaks(activity);
        consumeActivityDesLr(activity);
    }

    ///////////////////////////////////////////////////////////////////////////
    // act销毁回调
    ///////////////////////////////////////////////////////////////////////////
    private Map<Activity, Set<OnActivityDestroyListener>> mActDesLrs = new HashMap<>();//act销毁的列表，键值对

    /**
     * 添加clazz对应act销毁的回调
     *
     * @param act
     * @param lr
     */
    public void addActDesLr(Activity act, OnActivityDestroyListener lr) {
        if (Preconditions.isNulls(act, lr)) return;
        Set<OnActivityDestroyListener> lrs = mActDesLrs.get(act);
        if (Preconditions.isNull(lrs)) lrs = new HashSet<>();
        lrs.add(lr);
    }

    /**
     * 删除clazz对应act销毁的回调
     *
     * @param act
     */
    public void removeActDesLr(Activity act) {
        if (Preconditions.isNull(act) || Preconditions.isEmpty(mActDesLrs)) return;
        mActDesLrs.remove(act);
    }

    /**
     * 页面退出消费 act销毁回调
     *
     * @param act
     */
    public void consumeActivityDesLr(Activity act) {
        if (Preconditions.isNull(act) || Preconditions.isEmpty(mActDesLrs)) return;
        Set<OnActivityDestroyListener> lrs = mActDesLrs.remove(act);
        if (!Preconditions.isEmpty(lrs)) {
            for (OnActivityDestroyListener lr : lrs) {
                if (Preconditions.isNull(lr)) continue;
                lr.onActivityDestroy(act);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 前后台切换监听
    ///////////////////////////////////////////////////////////////////////////
    private List<OnAppStatusChangeListener> mAppChangeLrs = new LinkedList<>();//前后台切换监听列表

    /**
     * 添加前后台切换监听
     *
     * @param lr
     */
    public void addAppChangeLr(OnAppStatusChangeListener lr) {
        if (Preconditions.isNull(lr)) return;
        mAppChangeLrs.add(lr);
    }

    /**
     * 移除前后台切换监听
     */
    public void removeAppChangeLr(OnAppStatusChangeListener lr) {
        if (Preconditions.isNull(lr)) return;
        mAppChangeLrs.remove(lr);
    }

    ///////////////////////////////////////////////////////////////////////////
    // act栈
    ///////////////////////////////////////////////////////////////////////////
    private LinkedList<Activity> mActivityList = new LinkedList<>();

    /**
     * 设置栈顶act
     */
    private void setTopAct(Activity act) {
        if (Preconditions.isNull(act)) return;
        if (mActivityList.contains(act)) {
            if (act == mActivityList.getLast())
                mActivityList.removeLast();
            mActivityList.addLast(act);
        } else {
            mActivityList.addLast(act);
        }
    }

    /**
     * 获取栈顶act
     */
    public Activity getTopAct() {
        if (!Preconditions.isEmpty(mActivityList)) {
            Activity last = mActivityList.getLast();
            if (!Preconditions.isNull(last)) return last;
        }
        return getTopActByReflect();
    }

    /**
     * 清空act栈元素
     */
    public void clearActStack() {
        if (!Preconditions.isEmpty(mActivityList)) mActivityList.clear();
    }

    /**
     * 获取act栈内所有act
     *
     * @return
     */
    public LinkedList<Activity> getActivityList() {
        return mActivityList;
    }

    /**
     * 通知前后台切换
     *
     * @param isForeground
     */
    private void postStatus(boolean isForeground) {
        if (Preconditions.isEmpty(mAppChangeLrs)) return;
        for (OnAppStatusChangeListener lr : mAppChangeLrs) {
            if (Preconditions.isNull(lr)) continue;
            lr.onStatusChange(isForeground);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 工具方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 反射获取栈顶act
     */
    private Activity getTopActByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
            mActivityListField.setAccessible(true);
            Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
            if (activities == null) return null;
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 防止软键盘内存泄漏
     *
     * @param activity
     */
    private static void fixSoftInputLeaks(final Activity activity) {
        if (activity == null) return;
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews) {
            try {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (leakViewField == null) continue;
                if (!leakViewField.isAccessible()) {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getRootView() == activity.getWindow().getDecorView().getRootView()) {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore) { /**/ }
        }
    }
}
