package com.orange.thirdparty.image.glide;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orange.lib.common.config.DefaultConfig;
import com.orange.lib.common.globle.GlobleImpl;
import com.orange.lib.common.image.IImage;

public class GlideImageImpl implements IImage {
    private static volatile GlideImageImpl sInstance;

    private GlideImageImpl() {
    }

    public static GlideImageImpl getInstance() {
        if (null == sInstance) {
            synchronized (DefaultConfig.class) {
                if (null == sInstance) {
                    sInstance = new GlideImageImpl();
                }
            }
        }
        return sInstance;
    }

    /**
     * 设置网络图片
     *
     * @param iv
     * @param url
     */
    @Override
    public void loadImgUrl(ImageView iv, String url) {
        Glide.with(GlobleImpl.getInstance().getAppContext()).load(url).placeholder(GlobleImpl.getInstance().placeholder()).into(iv);
    }

    /**
     * 设置gif图片
     *
     * @param iv
     * @param resId
     */
    @Override
    public void loadImageResourceAsGif(ImageView iv, int resId) {
        Glide.with(GlobleImpl.getInstance().getAppContext()).asGif().load(resId).into(iv);
    }
}
