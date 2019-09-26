package com.orange.utils.common;

/**
 * @ProjectName: NdLib
 * @Package: com.orange.utils.common
 * @ClassName: Texts
 * @Description:
 * @Author: orange
 * @CreateDate: 2019/7/31 10:07
 * @UpdateUser:
 * @UpdateDate: 2019/7/31 10:07
 * @UpdateRemark:
 * @Version: 1.0
 */
public class Texts {
    /**
     * 判断字符串是不是为空或者全部是space
     *
     * @param str
     * @return
     */
    public static boolean isSpace(CharSequence str) {
        if (null == str || str.length() == 0) return true;
        for (int i = 0, len = str.length(); i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * 判断字符串是不是为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return null == str || str.length() == 0;
    }
}
