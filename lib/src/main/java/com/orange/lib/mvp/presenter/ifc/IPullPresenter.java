package com.orange.lib.mvp.presenter.ifc;

import com.orange.lib.mvp.view.ifc.Ipull;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:58
 */
public interface IPullPresenter extends INetPresenter {
    int COUNT_ONCE_LOAD = 10;

    /**
     * 默认10条，刷新、加载数据
     *
     * @param url       网络url
     * @param pageIndex 加载页码
     * @param pull      加载样式
     */
    void pullDatas(String url, int pageIndex, Ipull pull);

    /**
     * 刷新、加载数据
     *
     * @param url       网络url
     * @param pageIndex 加载页码
     * @param count     加载数量
     * @param pull      加载样式
     */
    void pullDatas(String url, int pageIndex, int count, Ipull pull);
}
