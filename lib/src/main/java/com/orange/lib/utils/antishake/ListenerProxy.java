package com.orange.lib.utils.antishake;

import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理OnClickListener，默认200毫秒防抖动
 */
public class ListenerProxy implements InvocationHandler {
    private View.OnClickListener mActualListener;

    private ListenerProxy(View.OnClickListener listener) {
        mActualListener = listener;
    }

    public static View.OnClickListener newListenerProxyInstance(View.OnClickListener listener) {
        return (View.OnClickListener) Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(), new Class[]{View.OnClickListener.class}, new ListenerProxy(listener));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!SingleClickUtils.isFastDoubleClick()) {
            return method.invoke(mActualListener, args);
        }
        return null;
    }
}
