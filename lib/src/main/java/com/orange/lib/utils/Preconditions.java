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
    public static boolean isSpace(CharSequence charSequence) {
        boolean ret = null == charSequence || 0 == charSequence.length();
        for (int i = 0, len = charSequence.length(); i < len; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) return false;
        }
        if (ret) Config.getInstance().getLog().d(LOG_PREFIX + "charSequence is isSpace");
        return ret;
    }

    /**
     * 判断字符串是不是空
     *
     * @param charSequences
     * @return
     */
    public static boolean isSpaces(CharSequence... charSequences) {
        boolean ret = isNull(charSequences);
        if (!ret) {
            for (CharSequence charSequence : charSequences) {
                ret |= isSpace(charSequence);
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
    public static <T, R> T needNotNull(T reference) {
        return needNotNull(reference, "null == reference");
    }

    /**
     * @param reference
     * @param <T>
     * @return
     */
    public static <T, R> T needNotNull(T reference, CharSequence charSequence) {
        if (null == reference) {
            Config.getInstance().getLog().e(isSpace(charSequence) ? "null == reference" : String.valueOf(charSequence));
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * @param references
     * @param <T>
     * @return
     */
    public static <T> void needNotNull(T... references) {
        needNotNull("references has null", references);
    }

    /**
     * @param references
     * @param <T>
     * @return
     */
    public static <T> void needNotNull(CharSequence charSequence, T... references) {
        if (null == references) {
            Config.getInstance().getLog().e(isSpace(charSequence) ? "references has null" : String.valueOf(charSequence));
            throw new NullPointerException();
        }
        for (T reference : references) {
            needNotNull(reference);
        }
    }
}
