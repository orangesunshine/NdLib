package com.orange.lib.utils.pull;

import android.view.View;

import androidx.core.util.Preconditions;

public class PageUtils {
    /**
     * 设置refreshView的pageIndex
     *
     * @param refreshView
     */
    public static void resetPageindexTag(View refreshView) {
        Preconditions.checkNotNull(refreshView);
        refreshView.setTag(1);
    }

    /**
     * pageindex＋
     */
    /**
     * @param refreshView
     */
    public static void pageIndexPlus(View refreshView) {
        Preconditions.checkNotNull(refreshView);
        int pageindex = 1;
        Object tag = refreshView.getTag();
        if (null != tag)
            pageindex = (int) tag;
        pageindex++;
        refreshView.setTag(pageindex);
    }

    /**
     * 获取当前页数下标
     *
     * @param refreshView
     * @return
     */
    public static int getPageindex(View refreshView) {
        Preconditions.checkNotNull(refreshView);
        int pageindex = 1;
        Object tag = refreshView.getTag();
        if (null != tag)
            pageindex = (int) tag;
        return pageindex;
    }

    /**
     * 判断是否加载
     *
     * @param refreshView
     * @return
     */
    public static boolean isLoadmore(View refreshView) {
        return getPageindex(refreshView) > 1;
    }
}
