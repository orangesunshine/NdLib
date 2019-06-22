package com.orange.lib.common.config;

import com.orange.lib.common.image.IImage;
import com.orange.lib.component.toast.DefaultToast;
import com.orange.lib.component.toast.IToast;

public class DefaultConfig implements IBuildFactory {
    private static volatile DefaultConfig sInstance;

    private DefaultConfig() {
    }

    public static DefaultConfig getInstance() {
        if (null == sInstance) {
            synchronized (DefaultConfig.class) {
                if (null == sInstance) {
                    sInstance = new DefaultConfig();
                }
            }
        }
        return sInstance;
    }

    @Override
    public IToast buildToast() {
        return new DefaultToast();
    }

    /**
     * 三方
     *
     * @return
     */
    @Override
    public IImage buildImage() {
        return null;
    }
}
