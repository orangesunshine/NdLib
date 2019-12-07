package com.orange.lib.mvp.model.net.netcancel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 14:13
 */
public class NetCancelManager {
    private static NetCancelManager mInstance;
    private List<INetCancel> mNetCancels = new ArrayList<>();//网络请求url、取消操作映射表

    private NetCancelManager() {
    }

    /**
     * 单例
     *
     * @return
     */
    public static NetCancelManager getInstance() {
        if (null == mInstance) {
            synchronized (NetCancelManager.class) {
                if (null == mInstance)
                    mInstance = new NetCancelManager();
            }
        }
        return mInstance;
    }

    public void registerNetCancel(INetCancel netCancel) {
        if (null != netCancel && !mNetCancels.contains(netCancel))
            mNetCancels.add(netCancel);
    }

    public void unregisterNetCancel(INetCancel netCancel) {
        if (null != netCancel && mNetCancels.contains(netCancel))
            mNetCancels.remove(netCancel);
    }
}
