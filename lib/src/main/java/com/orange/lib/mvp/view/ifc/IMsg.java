package com.orange.lib.mvp.view.ifc;

import androidx.annotation.StringRes;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 9:10
 */
public interface IMsg {
    /**
     * 提示消息
     *
     * @param charSequence 字符串
     */
    void showMsg(CharSequence charSequence);

    /**
     * 提示消息
     *
     * @param stringId 字符串资源id
     */
    void showMsg(@StringRes int stringId);
}
