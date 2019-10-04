package com.orange.lib.mvp.presenter;

import com.orange.lib.mvp.view.ifc.Ipull;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 9:09
 */
public interface IPullData {
    int COUNT_ONCE_LOAD = 10;

    /**
     * 刷新、加载数据
     *
     * @param pageIndex 加载页码
     * @param count     加载数量
     * @param pull      加载样式
     */
    void pullDatas(int pageIndex, int count, Ipull pull);
}
