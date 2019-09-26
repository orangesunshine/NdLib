package com.orange.thirdparty.aop;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.orange.lib.utils.antishake.SingleClickUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.ref.WeakReference;

import static com.orange.lib.utils.antishake.SingleClickUtils.DEFAULT_INTERVAL_MILLS;
import static com.orange.lib.utils.antishake.SingleClickUtils.MAX_INTERVAL_MILLS;
import static com.orange.lib.utils.antishake.SingleClickUtils.MIDDLE_INTERVAL_MILLS;

/**
 * @Author: orange
 * @CreateDate: 2019/9/17 10:01
 */
@Aspect
public class SingleClickAspect {
    private int[] m500millsIntervalViewIds = new int[]{};

    //特卡顿页面，防止重复进入
    private int[] m1500millsIntervalViewIds = new int[]{};
    private WeakReference<View> mLastClickViewReference;

    ///////////////////////////////////////////////////////////////////////////
    // onClick切点
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     * 注意：这里me.baron.test.annotation.SingleClick需要替换成
     * 你自己项目中SingleClick这个类的全路径哦
     */
    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClick() {
    }

    /**
     * 定义一个切面方法，包裹切点方法
     */
    @Around("onClick()")
    public void aroundOnClickJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出方法的参数
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        if (view == null) return;
        // 判断是否快速点击
        if (!SingleClickUtils.isFastDoubleClick(view, getInterval(view))) {
            // 不是快速点击，执行原方法
            joinPoint.proceed();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // act启动切点
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     * 注意：这里me.baron.test.annotation.SingleClick需要替换成
     * 你自己项目中SingleClick这个类的全路径哦
     */
    @Pointcut("execution(* android.app.Activity.startActivityForResult(android.content.Intent,int,android.os.Bundle))")
    public void onStartAct() {
    }

    /**
     * 定义一个切面方法，包裹切点方法
     */
    @Around("onStartAct()")
    public void aroundOnStartActJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (null != joinPoint) {
            Object[] args = joinPoint.getArgs();
            if (null != args && args.length > 0 && args[0] instanceof Intent) {
                Intent intent = (Intent) args[0];
                if (null != intent) {
                    ComponentName component = intent.getComponent();
                    if (null != component) {
                        String shortClassName = component.getShortClassName();
                        if (!TextUtils.isEmpty(shortClassName) && shortClassName.equals("BetActivity")) {

                        }
                    }
                }
            }
        }
        if (SingleClickUtils.isFastDoubleClick(DEFAULT_INTERVAL_MILLS))
            return;
        if (null != joinPoint)
            joinPoint.proceed();
    }

    int count;

    /**
     * 判断点击是不是同一个view
     *
     * @param view
     */
    private boolean isLastClickSameView(View view) {
        if (null == view) return false;
        if (null == mLastClickViewReference || view != mLastClickViewReference.get()) {
            mLastClickViewReference = new WeakReference<>(view);
            return false;
        }
        return view == mLastClickViewReference.get();
    }

    /**
     * 获取view快速点击间隔
     *
     * @param view
     * @return
     */
    private long getInterval(View view) {
        if (null == view) return DEFAULT_INTERVAL_MILLS;
        if (intervalRange(view, m500millsIntervalViewIds)) return MIDDLE_INTERVAL_MILLS;
        if (intervalRange(view, m1500millsIntervalViewIds)) return MAX_INTERVAL_MILLS;
        return DEFAULT_INTERVAL_MILLS;
    }

    /**
     * 判断view对应得间隔
     *
     * @param view
     * @param range
     * @return
     */
    private boolean intervalRange(View view, int[] range) {
        if (null == range || range.length == 0 || null == view) return false;
        for (int i : range) {
            if (view.getId() == i) return true;
        }
        return false;
    }
}
