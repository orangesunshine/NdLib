package com.orange.lib.common.bind;

import android.view.View;

public interface IBindView {
    /**
     * 绑定控件
     *
     * @param view 根视图
     */
    void bindViews(View view);

    /**
     * 解绑
     */
    void unbindView();
}
