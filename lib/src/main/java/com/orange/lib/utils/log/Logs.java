package com.orange.lib.utils.log;

import static com.orange.lib.constance.IConst.TAG_DEFAULT;

/**
 * @Author: orange
 * @CreateDate: 2019/9/26 9:42
 */
public class Logs {

    /**
     * 记录日志
     *
     * @param logs
     */
    public static final void logi(String... logs) {
        togi(TAG_DEFAULT, logs);
    }

    /**
     * 记录指定tag的日志
     *
     * @param tag
     * @param logs
     */
    public static final void togi(String tag, String... logs) {
        //ToDo
        System.out.println("Logs.togi.tag = " + tag);
    }

    /**
     * 记录错误信息
     *
     * @param logs
     */
    public static final void loge(String... logs) {
        toge(TAG_DEFAULT, logs);
    }

    /**
     * 记录指定tag错误信息
     *
     * @param tag
     * @param logs
     */
    public static final void toge(String tag, String... logs) {
        //ToDo
        System.out.println("Logs.toge.tag = " + tag);
    }

    /**
     * 记录precondition条件判断失败信息
     *
     * @param logs
     */
    public static final void logc(String... logs) {
        toge(TAG_DEFAULT, logs);
    }

    /**
     * 记录指定tag的precondition条件判断失败信息
     *
     * @param tag
     * @param logs
     */
    public static final void togc(String tag, String... logs) {
        //ToDo
        System.out.println("Logs.togc.tag = " + tag);
    }
}
