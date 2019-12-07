package com.orange.lib.activity.retry;

import android.view.View;

import com.orange.lib.common.holder.IHolder;

import java.lang.reflect.Method;

/**
 * @Author: orange
 * @CreateDate: 2019/11/28 11:50
 */
public class RetryListener implements IHolder.OnItemChildClickListener {
    Class mClazz;

    public RetryListener(Class clazz) {
        this.mClazz = clazz;
    }

    @Override
    public void onItemChildClick(View v) {
        while (null != mClazz) {
            boolean stop = false;
            String name = mClazz.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")) {
                break;
            }
            Method[] declaredMethods = mClazz.getDeclaredMethods();
            if (null != declaredMethods && declaredMethods.length > 0) {
                for (Method declaredMethod : declaredMethods) {
                    if (null != declaredMethod) {
                        Retry retry = declaredMethod.getAnnotation(Retry.class);
                        if (null != retry) {
                            try {
                                declaredMethod.invoke(this);
                            } catch (Exception e) {
                            }
                            stop = true;
                            break;
                        }
                    }
                }
            }
            if (stop) break;
            mClazz = mClazz.getSuperclass();
        }
    }
}
