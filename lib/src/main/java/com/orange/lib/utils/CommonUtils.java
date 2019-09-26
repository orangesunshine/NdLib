package com.orange.lib.utils;


import com.orange.lib.constance.IConst;

public class CommonUtils {
    /**
     * 判断网络code==200，true：成功|false：失败
     *
     * @param code
     * @return
     */
    public static boolean checkCodeSuccess(int code) {
        return IConst.CODE_SUCCESS == code;
    }
}
