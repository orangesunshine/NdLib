package com.orange.thirdparty.image.glide;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orange.lib.common.image.IImage;
import com.orange.lib.constance.IInitConst;
import com.orange.utils.base.Utils;

public class GlideImageImpl implements IImage {
    private static volatile GlideImageImpl sInstance;

    private GlideImageImpl() {
    }

    public static GlideImageImpl getInstance() {
        if (null == sInstance) {
            synchronized (GlideImageImpl.class) {
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
        Glide.with(Utils.getApp()).load(url).placeholder(IInitConst.sPlaceholderDrawable).into(iv);
    }

    /**
     * 设置gif图片
     *
     * @param iv
     * @param resId
     */
    @Override
    public void loadImageResourceAsGif(ImageView iv, int resId) {
        Glide.with(Utils.getApp()).asGif().load(resId).into(iv);
    }
}
