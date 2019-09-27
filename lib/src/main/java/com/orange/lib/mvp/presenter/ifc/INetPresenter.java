package com.orange.lib.mvp.presenter.ifc;

import com.orange.lib.mvp.view.ifc.ILoading;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:50
 */
public interface INetPresenter {
    /**
     * 获取网络数据
     *
     * @param url     网络url
     * @param loading 加载样式
     */
    void getNetDatas(String url, ILoading loading);
}
