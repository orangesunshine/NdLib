package com.orange.lib.common.config;

import com.orange.lib.common.image.IImage;
import com.orange.lib.loading.api.IUrlApi;
import com.orange.lib.utils.base.Preconditions;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 9:20
 */
public class Config {
    //static&final
    private static volatile Config ourInstance = null;
    private IUrlApi mUrlApi;//网络请求
    private IImage mIImage;//图片

    public static Config getInstance() {
        if (null == ourInstance) {
            synchronized (Config.class) {
                if (null == ourInstance) {
                    ourInstance = new Config();
                }
            }
        }
        return ourInstance;
    }

    private Config() {

    }

    /**
     * 配置网络请求实现
     */
    public void configNet(IUrlApi urlApi) {
        mUrlApi = urlApi;
    }

    /**
     * 获取网络请求实现
     *
     * @return
     */
    public IUrlApi getNet() {
        if (Preconditions.isNull(mUrlApi))
            mUrlApi = null;
        return mUrlApi;
    }

    /**
     * 配置图片实现
     */
    public void configImage(IImage iImage) {
        mIImage = iImage;
    }

    /**
     * 获取图片实现
     *
     * @return
     */
    public IImage getImage() {
        if (Preconditions.isNull(mIImage))
            mIImage = null;
        return mIImage;
    }
}
