package com.orange.lib.utils.toast;

import android.content.Context;

import androidx.annotation.DrawableRes;

import com.orange.lib.R;
import com.orange.lib.component.toast.CommonToast;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 11:17
 */
public class Toasts {
    private static CommonToast convertToast(Context context, @DrawableRes int drawableId, CharSequence charSequence) {
        return CommonToast.getInstance(context, R.layout.layout_toast_common, charSequence, (holder, data) -> {
            holder.setText(R.id.tv_toast, data);
            holder.setImageResource(R.id.iv_toast, drawableId);
        });
    }

    private static CommonToast convertToast(@DrawableRes int drawableId, CharSequence charSequence) {
        return CommonToast.getInstance(R.layout.layout_toast_common, charSequence, (holder, data) -> {
            holder.setText(R.id.tv_toast, data);
            holder.setImageResource(R.id.iv_toast, drawableId);
        });
    }

    // <editor-fold defaultstate="collapsed" desc="提示信息">
    public static void showNotice(CharSequence charSequence) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(R.drawable.toast_notice, charSequence).show();
    }

    public static void showNotice(CharSequence charSequence, int duration) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(R.drawable.toast_notice, charSequence).show(duration);
    }

    public static void showNotice(Context context, CharSequence charSequence) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(context, R.drawable.toast_notice, charSequence).show(context);
    }

    public static void showNotice(Context context, CharSequence charSequence, int duration) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(context, R.drawable.toast_notice, charSequence).show(context, duration);
    }

    // <editor-fold defaultstate="collapsed" desc="成功信息">
    public static void showSuccess(CharSequence charSequence) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(R.drawable.toast_success, charSequence).show();
    }

    public static void showSuccess(CharSequence charSequence, int duration) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(R.drawable.toast_success, charSequence).show(duration);
    }

    public static void showSuccess(Context context, CharSequence charSequence) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(context, R.drawable.toast_success, charSequence).show(context);
    }

    public static void showSuccess(Context context, CharSequence charSequence, int duration) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(context, R.drawable.toast_success, charSequence).show(context, duration);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="错误信息">
    public static void showError(CharSequence charSequence) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(R.drawable.toast_net_erro, charSequence).show();
    }

    public static void showError(CharSequence charSequence, int duration) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(R.drawable.toast_net_erro, charSequence).show(duration);
    }

    public static void showError(Context context, CharSequence charSequence) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(context, R.drawable.toast_net_erro, charSequence).show(context);
    }

    public static void showError(Context context, CharSequence charSequence, int duration) {
        if (null == charSequence || charSequence.length() == 0) return;
        convertToast(context, R.drawable.toast_net_erro, charSequence).show(context, duration);
    }
// </editor-fold>
}
