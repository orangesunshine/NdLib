package com.orange.lib.utils.base;

import com.orange.lib.utils.log.Logs;

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
        if (ret) Logs.logc(LOG_PREFIX + "obj is null");
        return null == ojb;
    }

    /**
     * 判断多个对象是不是空
     *
     * @param ojbs
     * @return
     */
    public static boolean isNulls(Object... ojbs) {
        boolean ret = isNull(ojbs);
        if (!ret) {
            for (Object ojb : ojbs) {
                ret &= isNull(ojb);
            }
        }
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
        if (ret) Logs.logc(LOG_PREFIX + "charSequence is empty");
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
                ret &= isEmpty(charSequence);
            }
        }
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
        ret &= collection.isEmpty();
        if (ret) Logs.logc(LOG_PREFIX + "collection is empty");
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
        ret &= map.isEmpty();
        if (ret) Logs.logc(LOG_PREFIX + "map is empty");
        return ret;
    }
}
