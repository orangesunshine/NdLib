package com.orange.lib.component.actbar;

import android.view.View;

/**
 * @Author: orange
 * @CreateDate: 2019/9/26 13:33
 */
public interface ICustomActionBar {
    /**
     * 自定义左侧控件
     *
     * @param view
     */
    void setLeftView(View view);

    /**
     * 自定义title
     */
    void setTitleView();

    /**
     * 设置右侧自定义view
     *
     * @param view
     */
    void setRightView(View view);
}
