package com.orange.lib.common.config;

import com.orange.lib.common.image.IImage;
import com.orange.lib.mvp.model.net.request.IRequest;
import com.orange.lib.utils.Preconditions;
import com.orange.lib.utils.log.ILog;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 9:20
 */
public class Config {
    //static&final
    private static volatile Config ourInstance = null;
    private IRequest mNetRequest;//网络请求
    private IImage mImage;//图片
    private ILog mLog;

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
    public void configNet(IRequest urlApi) {
        mNetRequest = urlApi;
    }

    /**
     * 获取网络请求实现
     *
     * @return
     */
    public IRequest getNet() {
        return mNetRequest;
    }

    /**
     * 配置图片实现
     */
    public void configImage(IImage iImage) {
        mImage = Preconditions.needNotNull(iImage);
    }

    /**
     * 获取图片实现
     *
     * @return
     */
    public IImage getImage() {
        return mImage;
    }

    public void configLog(ILog iLog) {
        mLog = Preconditions.needNotNull(iLog);
    }

    public ILog getLog() {
        return mLog;
    }
}
