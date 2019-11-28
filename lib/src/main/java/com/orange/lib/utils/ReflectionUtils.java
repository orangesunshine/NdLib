package com.orange.lib.utils;

import androidx.annotation.Nullable;

import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.view.activity.NetActivity;
import com.orange.lib.pull.request.IPageNetRequest;
import com.orange.lib.utils.text.TextUtils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public class ReflectionUtils {
    private static int COUNT_MAX_RECURSIVE = 3;

    public static Type getGenericActualTypeArg(Class clazz) {
        return getGenericSuperclassActualTypeArg(clazz);
    }

    /**
     * 递归第一个接口
     *
     * @param clazz
     * @return
     */
    public static Type getGenericInterfacesActualTypeArg(Class clazz) {
        int count = COUNT_MAX_RECURSIVE;
        while (null != clazz && count > 0) {
            count--;
            Type type = null;
            Type[] types = clazz.getGenericInterfaces();
            if (null != types && types.length > 0)
                type = types[0];
            if (null == type) return null;
            if (type instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                if (null != actualTypeArguments && actualTypeArguments.length > 0)
                    return actualTypeArguments[0];
            }
            clazz = type.getClass();
        }
        return null;
    }

    /**
     * 递归第一个接口
     *
     * @param clazz
     * @return
     */
    public static Type getGenericSuperclassActualTypeArg(Class clazz) {
        int count = COUNT_MAX_RECURSIVE;
        while (null != clazz && count > 0) {
            count--;
            Type type = clazz.getGenericSuperclass();
            if (null == type) return null;
            if (type instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                if (null != actualTypeArguments && actualTypeArguments.length > 0)
                    return actualTypeArguments[0];
            }
            clazz = type.getClass();
        }
        return null;
    }

    /**
     * 获取父类（NetActivity）泛型实参类型的实例
     *
     * @param netActivity
     * @param <P>
     * @return
     */
    public static <P extends INetContact.Presenter> P getGenericActualTypeArgInstance(NetActivity<P> netActivity) {
        Type type = null;
        type = netActivity.getClass().getGenericSuperclass();
        if (null == type || !(type instanceof ParameterizedType)) {
            Type[] types = netActivity.getClass().getGenericInterfaces();
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

    public static boolean hasUnresolvableType(@Nullable Type type) {
        if (type instanceof Class<?>) {
            return false;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
                if (hasUnresolvableType(typeArgument)) {
                    return true;
                }
            }
            return false;
        }
        if (type instanceof GenericArrayType) {
            return hasUnresolvableType(((GenericArrayType) type).getGenericComponentType());
        }
        if (type instanceof TypeVariable) {
            return true;
        }
        if (type instanceof WildcardType) {
            return true;
        }
        String className = type == null ? "null" : type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + className);
    }
}
