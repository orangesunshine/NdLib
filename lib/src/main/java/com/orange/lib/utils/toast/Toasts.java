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

    /**
     * 提示信息
     */
    public static void showMsg(CharSequence charSequence) {
        convertToast(R.drawable.ic_photo_gray, charSequence).show();
    }

    /**
     * 提示信息
     */
    public static void showMsg(CharSequence charSequence, int duration) {
        convertToast(R.drawable.ic_photo_gray, charSequence).show(duration);
    }

    /**
     * 提示信息
     */
    public static void showMsg(Context context, CharSequence charSequence) {
        convertToast(context, R.drawable.ic_photo_gray, charSequence).show(context);
    }

    /**
     * 提示信息
     */
    public static void showMsg(Context context, CharSequence charSequence, int duration) {
        convertToast(context, R.drawable.ic_photo_gray, charSequence).show(context, duration);
    }

    /**
     * 显示错误信息
     */
    public static void showError(CharSequence charSequence) {
        convertToast(R.drawable.ic_photo_gray, charSequence).show();
    }

    /**
     * 显示错误信息
     */
    public static void showError(CharSequence charSequence, int duration) {
        convertToast(R.drawable.ic_photo_gray, charSequence).show(duration);
    }

    /**
     * 显示错误信息
     */
    public static void showError(Context context, CharSequence charSequence) {
        convertToast(context, R.drawable.ic_photo_gray, charSequence).show(context);
    }

    /**
     * 显示错误信息
     */
    public static void showError(Context context, CharSequence charSequence, int duration) {
        convertToast(context, R.drawable.ic_photo_gray, charSequence).show(context, duration);
    }
}
