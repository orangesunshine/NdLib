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
    public static final void i(String... logs) {
        info(TAG_DEFAULT, logs);
    }

    /**
     * 记录指定tag的日志
     *
     * @param tag
     * @param logs
     */
    public static final void info(String tag, String... logs) {
        //ToDo
        System.out.println("Logs.info.tag = " + tag);
    }

    /**
     * 记录错误信息
     *
     * @param logs
     */
    public static final void e(String... logs) {
        error(TAG_DEFAULT, logs);
    }

    /**
     * 记录指定tag错误信息
     *
     * @param tag
     * @param logs
     */
    public static final void error(String tag, String... logs) {
        //ToDo
        System.out.println("Logs.error.tag = " + tag);
    }

    /**
     * 记录precondition条件判断失败信息
     *
     * @param logs
     */
    public static final void c(String... logs) {
        error(TAG_DEFAULT, logs);
    }

    /**
     * 记录指定tag的precondition条件判断失败信息
     *
     * @param tag
     * @param logs
     */
    public static final void condition(String tag, String... logs) {
        //ToDo
        System.out.println("Logs.condition.tag = " + tag);
    }
}
