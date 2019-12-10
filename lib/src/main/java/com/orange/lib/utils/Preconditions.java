package com.orange.lib.utils;

import com.orange.lib.common.config.Config;

import java.util.Collection;
import java.util.Map;

public class Preconditions {
    static String LOG_PREFIX = "Preconditions record: ";//Preconditions日志前缀

    /**
     * 判断对象是不是空
     *
     * @param ojb
     * @return
     */
    public static boolean isNull(Object ojb) {
        boolean ret = null == ojb;
        if (ret) Config.getInstance().getLog().d(LOG_PREFIX + "obj is null");
        return ret;
    }

    /**
     * 判断多个对象是不是null
     *
     * @param objs
     * @return
     */
    public static boolean isNulls(Object... objs) {
        boolean ret = isEmpty(objs);
        if (!ret) {
            for (Object ojb : objs) {
                ret |= isNull(ojb);
            }
        }
        if (ret) Config.getInstance().getLog().d(LOG_PREFIX + "objs is nulls");
        return ret;
    }

    /**
     * 判断多个对象是不是空
     *
     * @param objs
     * @return
     */
    public static boolean isEmpty(Object... objs) {
        boolean ret = isNull(objs);
        if (!ret) ret |= 0 == objs.length;
        if (ret) Config.getInstance().getLog().d(LOG_PREFIX + "objs is empty");
        return ret;
    }

    /**
     * 判断多个字符串是不是空
     *
     * @param charSequence
     * @return
     */
    public static boolean isEmpty(CharSequence charSequence) {
        boolean ret = null == charSequence || 0 == charSequence.length();
        if (ret) Config.getInstance().getLog().d(LOG_PREFIX + "charSequence is empty");
        return ret;
    }

    /**
     * 判断字符串是不是空
     *
     * @param charSequences
     * @return
     */
    public static boolean isEmptys(CharSequence... charSequences) {
        boolean ret = isNull(charSequences);
        if (!ret) {
            for (CharSequence charSequence : charSequences) {
                ret |= isEmpty(charSequence);
            }
        }
        if (ret) Config.getInstance().getLog().d(LOG_PREFIX + "charSequences is isEmptys");
        return ret;
    }

    /**
     * 判断list是不是空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        boolean ret = isNull(collection);
        if (!ret) ret |= collection.isEmpty();
        if (ret) Config.getInstance().getLog().d(LOG_PREFIX + "collection is empty");
        return ret;
    }

    /**
     * 判断list是不是空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        boolean ret = isNull(map);
        if (!ret) ret |= map.isEmpty();
        if (ret) Config.getInstance().getLog().d(LOG_PREFIX + "map is empty");
        return ret;
    }

    /**
     * 判断list是不是空
     *
     * @param condition
     * @return
     */
    public static boolean condition(boolean condition) {
        if (condition) Config.getInstance().getLog().d(LOG_PREFIX + "condition: " + condition);
        return condition;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 新方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param reference
     * @param <T>
     * @return
     */
    public static <T,R> T needNotNull(T reference) {
        if (null == reference) {
            Config.getInstance().getLog().e("null == reference");
            throw new NullPointerException();
        }
        return reference;
    }
}
