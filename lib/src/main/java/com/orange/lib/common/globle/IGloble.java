package com.orange.lib.common.globle;

import android.content.Context;

import androidx.annotation.DrawableRes;

public interface IGloble {
    //获取全局context
    Context getAppContext();

    /**
     * 屏幕宽度
     * @return
     */
    int getScreenWidth();

    /**
     *
     * @return
     */
    int getScreenHeight();

    //占位图
    @DrawableRes
    int placeholder();
}
