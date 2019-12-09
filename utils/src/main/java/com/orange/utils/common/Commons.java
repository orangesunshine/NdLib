package com.orange.utils.common;


public class Commons {
    /**
     * 判断网络code==200，true：成功|false：失败
     *
     * @param code
     * @return
     */
    public static boolean checkCodeSuccess(int code) {
        return 200 == code;
    }
}
