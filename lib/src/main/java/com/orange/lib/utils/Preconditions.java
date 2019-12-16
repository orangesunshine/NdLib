package com.orange.lib.utils;

import com.orange.lib.common.config.Config;

import java.util.Collection;
import java.util.Map;

public class Preconditions {
    static String LOG_PREFIX = "Preconditions record: ";//Preconditions日志前缀

    private static void log(boolean ret, CharSequence log) {
        if (ret && !isSpace(log))
            Config.getInstance().getLog().d(LOG_PREFIX + log);
    }

    /**
     * 判断对象是不是空
     *
     * @param ojb
     * @return
     */
    public static boolean isNull(Object ojb) {
        return isNull(ojb, null);
    }

    public static boolean isNull(Object ojb, CharSequence log) {
        boolean ret = null == ojb;
        log(ret, log);
        return ret;
    }

    /**
     * 判断多个对象是不是null
     *
     * @param objs
     * @return
     */
    public static boolean isNulls(Object... objs) {
        return isNulls(null, objs);
    }

    public static boolean isNulls(CharSequence log, Object... objs) {
        boolean ret = isEmpty(objs);
        if (!ret) {
            for (Object ojb : objs) {
                ret |= isNull(ojb);
            }
        }
        log(ret, log);
        return ret;
    }

    /**
     * 判断多个对象是不是空
     *
     * @param objs
     * @return
     */
    public static boolean isEmpty(Object... objs) {
        return isEmpty(null, objs);
    }

    public static boolean isEmpty(CharSequence log, Object... objs) {
        boolean ret = isNull(objs);
        if (!ret) ret |= 0 == objs.length;
        log(ret, log);
        return ret;
    }

    /**
     * 判断多个字符串是不是空
     *
     * @param charSequence
     * @return
     */
    public static boolean isSpace(CharSequence charSequence) {
        return isSpace(charSequence, null);
    }

    public static boolean isSpace(CharSequence charSequence, CharSequence log) {
        boolean ret = null == charSequence || 0 == charSequence.length();
        if (!ret)
            for (int i = 0, len = charSequence.length(); i < len; i++) {
                if (!Character.isWhitespace(charSequence.charAt(i))) return false;
            }
        return ret;
    }

    /**
     * 判断字符串是不是空
     *
     * @param charSequences
     * @return
     */
    public static boolean isSpaces(CharSequence... charSequences) {
        return isSpaces(null, charSequences);
    }

    public static boolean isSpaces(CharSequence log, CharSequence... charSequences) {
        boolean ret = isNull(charSequences);
        if (!ret) {
            for (CharSequence charSequence : charSequences) {
                ret |= isSpace(charSequence);
            }
        }
        log(ret, log);
        return ret;
    }

    /**
     * 判断list是不是空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return isEmpty(collection, null);
    }

    public static boolean isEmpty(Collection collection, CharSequence log) {
        boolean ret = isNull(collection);
        if (!ret) ret |= collection.isEmpty();
        log(ret, log);
        return ret;
    }

    /**
     * 判断list是不是空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        return isEmpty(map, null);
    }

    public static boolean isEmpty(Map map, CharSequence log) {
        boolean ret = isNull(map);
        if (!ret) ret |= map.isEmpty();
        log(ret, log);
        return ret;
    }

    /**
     * 判断list是不是空
     *
     * @param condition
     * @return
     */
    public static boolean condition(boolean condition, CharSequence log) {
        log(condition, log);
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
    public static <T, R> T needNotNull(T reference, CharSequence log) {
        boolean ret = null == reference;
        log(ret, isSpace(log) ? "references has null" : String.valueOf(log));
        if (ret) throw new NullPointerException();
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
    public static <T> void needNotNull(CharSequence log, T... references) {
        boolean ret = null == references;
        log(ret, isSpace(log) ? "references has null" : String.valueOf(log));
        if (ret) throw new NullPointerException();
        for (T reference : references) {
            needNotNull(reference);
        }
    }
}
