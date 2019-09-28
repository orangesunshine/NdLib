package com.orange.ndlib.activity.base.loading;

import com.orange.lib.mvp.view.ifc.base.INetView;

import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 11:08
 */
public interface ILoadingDemoView extends INetView {
    void setLoadingDatas(String data);
    void setPullDatas(List<String> data);
}
