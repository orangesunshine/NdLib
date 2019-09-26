package com.orange.lib.utils.base;

/**
 * @Author: orange
 * @CreateDate: 2019/9/26 9:25
 */
public interface OnAppStatusChangeListener {
    //切换前后台回调
    void onStatusChange(boolean isForeground);
}
