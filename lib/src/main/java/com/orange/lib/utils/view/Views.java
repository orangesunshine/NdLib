package com.orange.lib.utils.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.orange.lib.R;
import com.orange.lib.common.globle.GlobleImpl;
import com.orange.lib.utils.base.Preconditions;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.orange.lib.constance.ITag.TAG_COPY;

public class Views {
    /**
     * 设置文本
     *
     * @param tv
     * @param stringRes
     */
    public static void setText(TextView tv, @StringRes int stringRes) {
        if (Preconditions.isNull(tv)) return;
        tv.setText(stringRes);
    }

    /**
     * 设置文本
     *
     * @param tv
     * @param text
     */
    public static void setText(TextView tv, CharSequence text) {
        if (Preconditions.isNull(tv)) return;
        if (null == text) text = "";
        tv.setText(text);
    }

    /**
     * 设置textSize
     *
     * @param tv
     * @param size
     */
    public static void setTextSize(TextView tv, float size) {
        if (Preconditions.isNull(tv)) return;
        if (size < 0) return;
        tv.setTextSize(size);
    }

    /**
     * 设置文本
     *
     * @param tv
     * @param color
     */
    public static void setTextColor(TextView tv, @ColorInt int color) {
        if (Preconditions.isNull(tv)) return;
        tv.setTextColor(color);
    }

    /**
     * 设置图片
     *
     * @param iv
     * @param resId
     */
    public static void setImageResource(ImageView iv, int resId) {
        if (Preconditions.isNull(iv)) return;
        if (resId <= 0) resId = R.drawable.shape_picture_placeholder;
        iv.setImageResource(resId);
    }

    /**
     * 设置visibile
     *
     * @param view
     * @param visible
     */
    public static void setVisible(View view, boolean visible) {
        if (Preconditions.isNull(view)) return;
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置select
     *
     * @param view
     * @param selected
     */
    public static void setSelect(View view, boolean selected) {
        if (Preconditions.isNull(view)) return;
        view.setSelected(selected);
    }

    /**
     * @param view
     * @param color
     * @return
     */
    public static void setBackgroudColor(View view, @ColorInt int color) {
        if (Preconditions.isNull(view)) return;
        view.setBackgroundColor(color);
    }

    /**
     * @param view
     * @param resId
     * @return
     */
    public static void setBackgroundResource(View view, @DrawableRes int resId) {
        if (Preconditions.isNull(view)) return;
        view.setBackgroundResource(resId);
    }

    public static void setOnClickListener(View view, View.OnClickListener listener) {
        if (Preconditions.isNull(view)) return;
        view.setOnClickListener(listener);
    }

    public static boolean copy2Clipboard(Context context, TextView tv) {
        if (Preconditions.isNull(tv)) return false;
        String trim = tv.getText().toString().trim();
        if (!Preconditions.isEmpty(trim)) {
            //获取剪贴版
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            //创建ClipData对象
            //第一个参数只是一个标记，随便传入。
            //第二个参数是要复制到剪贴版的内容
            ClipData clip = ClipData.newPlainText(TAG_COPY, trim);
            //传入clipdata对象.
            clipboard.setPrimaryClip(clip);
            return true;
        }
        return false;
    }

    /**
     * imageview设置圆形图片
     *
     * @param ivImage
     * @param context
     * @param drawRes
     */
    public static void setCircleImage(ImageView ivImage, Context context, @DrawableRes int drawRes) {
        if (null == ivImage || null == context) return;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawRes);
        if (null != bitmap)
            ivImage.setImageBitmap(circleDrawable(context, bitmap));
    }

    /**
     * imageview设置圆形图片
     *
     * @param ivImage
     * @param context
     * @param urlString
     */
    public static void setCircleImage(final ImageView ivImage, final Context context, final String urlString) {
        if (null == ivImage || null == context) return;
        if (TextUtils.isEmpty(urlString)) {
            setCircleImage(ivImage, context, GlobleImpl.getInstance().placeholder());//placeholder
        } else if (Patterns.WEB_URL.matcher(urlString).matches()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection conn = null;
                    try {
                        URL url = new URL(urlString);
                        if (null != url) {
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            final Bitmap bitmap = BitmapFactory.decodeStream(is);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    ivImage.setImageBitmap(circleDrawable(context, bitmap));
                                }
                            });
                        }
                    } catch (Exception e) {
                    } finally {
                        if (null != conn)
                            conn.disconnect();
                    }
                }
            }).start();

        }
    }


    /**
     * 获取圆形drawable
     *
     * @param bitmap
     * @return
     */
    private static Bitmap circleDrawable(Context context, Bitmap bitmap) {
        if (null == context || null == bitmap) return null;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int diameter = Math.min(width, height);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawCircle(width / 2, height / 2, diameter / 2, paint);
        if (null != bitmap && !bitmap.isRecycled())
            bitmap.recycle();
        return newBitmap;
    }
}
