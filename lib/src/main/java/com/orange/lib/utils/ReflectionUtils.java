package com.orange.lib.utils;

import androidx.core.util.Preconditions;

import com.orange.lib.loading.callback.INetCallback;
import com.orange.lib.pull.request.IPageNetRequest;
import com.orange.lib.mvp.presenter.BasePresenter;
import com.orange.lib.mvp.view.activity.PresenterActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {
    /**
     * 获取父类（INetCallback）泛型实参类型
     *
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Type getGenericActualTypeArg(INetCallback<T> callback) {
        Preconditions.checkNotNull(callback, "null == callback");
        Type type = null;
        Type[] types = callback.getClass().getGenericInterfaces();
        if (null != types && types.length > 0)
            type = types[0];
        if (null == type || !(type instanceof ParameterizedType))
            type = callback.getClass().getGenericSuperclass();
        if (null == type || !(type instanceof ParameterizedType)) return null;
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (null != actualTypeArguments && actualTypeArguments.length > 0)
            return actualTypeArguments[0];
        return null;
    }

    /**
     * 获取父类（PresenterActivity）泛型实参类型的实例
     *
     * @param presenterActivity
     * @param <P>
     * @return
     */
    public static <P extends BasePresenter> P getGenericActualTypeArgInstance(PresenterActivity<P> presenterActivity) {
        Preconditions.checkNotNull(presenterActivity);
        Type type = null;
        type = presenterActivity.getClass().getGenericSuperclass();
        if (null == type || !(type instanceof ParameterizedType)) {
            Type[] types = presenterActivity.getClass().getGenericInterfaces();
            if (null != types && types.length > 0)
                type = types[0];
        }
        if (null == type || !(type instanceof ParameterizedType)) return null;
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (null != actualTypeArguments && actualTypeArguments.length > 0) {
            Type actualTypeArgument = actualTypeArguments[0];
            try {
                if (null != actualTypeArgument) {
                    String typeName = actualTypeArgument.toString();
                    if (!TextUtils.isEmpty(typeName)) {
                        Object instance = null;
                        String prefix = "class ";
                        while (null == instance) {
                            try {
                                instance = Class.forName(typeName).newInstance();
                            } catch (Exception e) {
                                if (typeName.startsWith(prefix)) {
                                    typeName = typeName.substring(prefix.length());
                                }
                            }
                        }
                        return (P) instance;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取IPageNetRequest的泛型type
     *
     * @param pageNetRequest
     * @param <T>
     * @return
     */
    public static <T> Type pageNetRequestGenericType(IPageNetRequest<T> pageNetRequest) {
        Preconditions.checkNotNull(pageNetRequest);
        Type type = null;
        if (null != pageNetRequest) {
            Type[] types = pageNetRequest.getClass().getGenericInterfaces();
            if (null != types && types.length > 0)
                type = types[0];
            if (null == type || !(type instanceof ParameterizedType))
                type = pageNetRequest.getClass().getGenericSuperclass();
            if (null == type || !(type instanceof ParameterizedType)) return null;
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            if (null != actualTypeArguments && actualTypeArguments.length > 0)
                return actualTypeArguments[0];
        }
        return type;
    }
}
