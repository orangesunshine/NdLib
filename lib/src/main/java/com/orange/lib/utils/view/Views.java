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
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.orange.lib.R;
import com.orange.lib.common.convert.IHolderConvert;
import com.orange.lib.common.holder.CommonHolder;
import com.orange.lib.constance.IInitConst;
import com.orange.lib.constance.IViewConst;
import com.orange.lib.utils.Preconditions;
import com.orange.utils.common.Bars;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.orange.lib.constance.ITag.LABEL_COPY;


public class Views {
    /**
     * ViewStub占位 动态添加布局文件
     *
     * @param stub
     * @param layoutId
     */
    public static View attachStub(ViewStub stub, int layoutId) {
        if (!Preconditions.isNull(stub)) {
            stub.setLayoutResource(layoutId);
            return stub.inflate();
        }
        return null;
    }

    /**
     * 数据填充到StubView
     *
     * @param stub
     * @param layoutId
     * @param data
     * @param holderConvert
     * @param <T>
     */
    public static <T> CommonHolder convertData2StubView(ViewStub stub, int layoutId, T data, IHolderConvert<T> holderConvert) {
        return convertData2View(attachStub(stub, layoutId), data, holderConvert);
    }

    /**
     * 数据填充到view
     *
     * @param holderConvert
     * @param data
     * @param <T>
     */
    public static <T> CommonHolder convertData2View(View view, T data, IHolderConvert<T> holderConvert) {
        if (!Preconditions.isNulls(holderConvert, view, data)) {
            CommonHolder commonHolder = new CommonHolder(view);
            holderConvert.convert(commonHolder, data);
            return commonHolder;
        }
        return null;
    }

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
     * 动态设置控件高度
     *
     * @param view
     * @param height
     */
    public static void setHeight(View view, int height) {
        if (Preconditions.isNull(view)) return;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = Bars.getStatusBarHeight();
        view.setLayoutParams(lp);
    }

    /**
     * 动态设置控件宽度
     *
     * @param view
     * @param width
     */
    public static void setWidth(View view, int width) {
        if (Preconditions.isNull(view)) return;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
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

    /**
     * 设置状态栏高度
     *
     * @param statusBar
     */
    public static void setStatusBarHeight(View statusBar) {
        setStatusBarHeight(statusBar, IViewConst.HEIGHT_STATUSBAR);
    }

    /**
     * 设置状态栏高度
     *
     * @param statusBar
     * @param height
     */
    public static void setStatusBarHeight(View statusBar, int height) {
        setHeight(statusBar, height);
    }

    public static void setOnClickListener(View view, View.OnClickListener listener) {
        if (Preconditions.isNull(view)) return;
        view.setOnClickListener(listener);
    }

    public static boolean copy2Clipboard(Context context, TextView tv) {
        if (Preconditions.isNull(tv)) return false;
        String trim = tv.getText().toString().trim();
        if (!Preconditions.isSpace(trim)) {
            //获取剪贴版
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            //创建ClipData对象
            //第一个参数只是一个标记，随便传入。
            //第二个参数是要复制到剪贴版的内容
            ClipData clip = ClipData.newPlainText(LABEL_COPY, trim);
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
            setCircleImage(ivImage, context, IInitConst.sPlaceholderDrawable);//placeholder
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
