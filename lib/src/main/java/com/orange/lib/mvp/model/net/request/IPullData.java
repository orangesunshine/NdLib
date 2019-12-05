package com.orange.lib.mvp.model.net.request;

import com.orange.lib.mvp.model.net.request.request.PullParams;
import com.orange.lib.mvp.view.pull.IPull;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 9:09
 */
public interface IPullData {
    /**
     * 刷新、加载数据
     *
     * @param pageIndex 加载页码
     * @param count     加载数量
     * @param pull      加载样式
     */
    void pullDatas(int pageIndex, int count, IPull pull);

    void OnRefresh(PullParams pullnetRequest);

    void onLoadmore();
}
