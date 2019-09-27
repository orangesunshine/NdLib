package com.orange.lib.mvp.view.ifc;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:44
 */
public interface Ipull {
    /**
     * 设置能否刷新
     *
     * @param enable
     */
    void enableRefresh(boolean enable);

    /**
     * 设置能否加载
     *
     * @param enable
     */
    void enableLoadmore(boolean enable);
}
