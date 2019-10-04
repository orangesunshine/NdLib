package com.orange.lib.mvp.model.net.netcancel;

import com.orange.lib.utils.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 14:13
 */
public class NetCancelManager {
    private static NetCancelManager mInstance;
    private Map<String, INetCancel> netCancelMap = new HashMap<>();//网络请求url、取消操作映射表

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

    /**
     * 通知取消
     *
     * @param url
     */
    public void notifyNetCancel(String url) {
        if (Preconditions.isEmpty(netCancelMap)) return;
        if (netCancelMap.containsKey(url)) {
            INetCancel iNetCancel = netCancelMap.get(url);
            if (!Preconditions.isNull(iNetCancel)) {
                iNetCancel.cancel();
                netCancelMap.remove(url);
            }
        }
    }

    public void notifyNetCancel(INetCancel netCancel) {

    }

    private void removeNetCancel(INetCancel netCancel) {
    }
}
