package com.orange.lib.common.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.StringRes;

import com.orange.lib.R;
import com.orange.lib.utils.view.Views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonHolder implements IHolder {
    //所有控件的集合
    protected SparseArray<View> mViews;
    protected View itemView;
    //复用的View
    protected Map<OnItemChildClickListener, List<Integer>> mOnItemChildClickListenerMaps;
    protected OnItemClickListener mOnItemClickListener;

    /**
     * 解析布局文件生成holder
     *
     * @param context
     * @param parent
     * @param layoutId
     * @return
     */
    public static CommonHolder create(Context context, ViewGroup parent, int layoutId) {
        return create(LayoutInflater.from(context).inflate(layoutId, parent, false));
    }

    /**
     * views生成holder
     *
     * @param view
     * @return
     */
    public static CommonHolder create(View view) {
        return new CommonHolder(view);
    }

    /**
     * 构造函数
     *
     * @param itemView item
     */
    public CommonHolder(View itemView) {
        this.itemView = itemView;
        mViews = new SparseArray<>();
        //构造方法中就指定布局
        itemView.setTag(R.id.id_holder_orange, this);
    }

    /**
     * 通过ViewId获取控件
     *
     * @param viewId
     * @return
     */
    @Override
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 根据viewId获取控件
     *
     * @param viewId
     * @return
     */
    @Override
    public TextView getTextView(int viewId) {
        return getView(viewId);
    }

    /**
     * 根据viewId获取控件
     *
     * @param viewId
     * @return
     */
    @Override
    public ImageView getImageView(int viewId) {
        return getView(viewId);
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     * @return
     */
    @Override
    public IHolder setText(int viewId, CharSequence text) {
        return setText(getView(viewId), text);
    }

    /**
     * 设置文本
     *
     * @param tv
     * @param text
     * @return
     */
    @Override
    public IHolder setText(TextView tv, CharSequence text) {
        Views.setText(tv, text);
        return this;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param stringRes
     */
    @Override
    public IHolder setText(int viewId, int stringRes) {
        return setText(getView(viewId), stringRes);
    }

    /**
     * 设置文本
     *
     * @param tv
     * @param stringRes
     */
    @Override
    public IHolder setText(TextView tv, @StringRes int stringRes) {
        Views.setText(tv, stringRes);
        return this;
    }

    /**
     * 设置文本大小
     *
     * @param viewId
     * @param size
     * @return
     */
    @Override
    public IHolder setTextSize(int viewId, float size) {
        return setTextSize(getView(viewId), size);
    }

    /**
     * 设置文本大小
     *
     * @param tv
     * @param size
     * @return
     */
    @Override
    public IHolder setTextSize(TextView tv, float size) {
        Views.setTextSize(tv, size);
        return this;
    }

    /**
     * 设置文本颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    @Override
    public IHolder setTextColor(int viewId, @ColorInt int color) {
        return setTextColor(getView(viewId), color);
    }

    /**
     * 设置文本颜色
     *
     * @param tv
     * @param color
     * @return
     */
    @Override
    public IHolder setTextColor(TextView tv, @ColorInt int color) {
        Views.setTextColor(tv, color);
        return this;
    }

    /**
     * 设置图片资源
     *
     * @param viewId
     * @param resId
     * @return
     */
    @Override
    public IHolder setImageResource(int viewId, int resId) {
        return setImageResource(getView(viewId), resId);
    }

    /**
     * 设置图片资源
     *
     * @param iv
     * @param resId
     * @return
     */
    @Override
    public IHolder setImageResource(ImageView iv, int resId) {
        Views.setImageResource(iv, resId);
        return this;
    }

    /**
     * 设置背景图片资源
     *
     * @param viewId
     * @param resId
     * @return
     */
    @Override
    public IHolder setBackgroundResource(int viewId, int resId) {
        return setBackgroundResource(getView(viewId), resId);
    }

    /**
     * 设置背景图片资源
     *
     * @param view
     * @param resId
     * @return
     */
    @Override
    public IHolder setBackgroundResource(View view, int resId) {
        Views.setBackgroundResource(view, resId);
        return this;
    }

    /**
     * 加载gif图片资源
     *
     * @param viewId
     * @param resId
     * @return
     */
    @Override
    public IHolder loadImageResourceAsGif(int viewId, int resId) {
        return loadImageResourceAsGif(getView(viewId), resId);
    }

    /**
     * 加载gif图片资源
     *
     * @param iv
     * @param resId
     * @return
     */
    @Override
    public IHolder loadImageResourceAsGif(ImageView iv, int resId) {
        return this;
    }

    /**
     * viewId设置可见性
     *
     * @param viewId
     * @param visible
     * @return
     */
    @Override
    public IHolder setVisible(int viewId, boolean visible) {
        return setVisible(getView(viewId), visible);
    }

    /**
     * view设置可见性
     *
     * @param view
     * @param visible
     * @return
     */
    @Override
    public IHolder setVisible(View view, boolean visible) {
        Views.setVisible(view, visible);
        return this;
    }

    /**
     * viewId设置选中
     *
     * @param viewId
     * @param selected
     * @return
     */
    @Override
    public IHolder setSelect(int viewId, boolean selected) {
        return setSelect(getView(viewId), selected);
    }

    /**
     * view设置选中
     *
     * @param view
     * @param selected
     * @return
     */
    @Override
    public IHolder setSelect(View view, boolean selected) {
        Views.setSelect(view, selected);
        return this;
    }

    @Override
    public IHolder setHeight(int viewId, int height) {
        return setHeight(getView(viewId), height);
    }

    @Override
    public IHolder setHeight(View view, int height) {
        Views.setHeight(view,height);
        return this;
    }

    @Override
    public IHolder setWidth(int viewId, int width) {
        return setWidth(getView(viewId), width);
    }

    @Override
    public IHolder setWidth(View view, int width) {
        Views.setWidth(view,width);
        return this;
    }

    /**
     * 设置项点击事件
     *
     * @param listener
     * @return
     */
    @Override
    public IHolder setOnItemClick(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        if (null != itemView)
            itemView.setOnClickListener(this);
        return this;
    }

    /**
     * 设置项子控件点击事件
     *
     * @param listener
     * @param viewIds
     * @return
     */
    @Override
    public IHolder addOnItemChildClick(OnItemChildClickListener listener, int... viewIds) {
        if (null == viewIds || viewIds.length == 0) return this;
        if (null == mOnItemChildClickListenerMaps)
            mOnItemChildClickListenerMaps = new HashMap<>();
        List<Integer> idList = mOnItemChildClickListenerMaps.get(listener);
        if (null == idList) {
            idList = new ArrayList<>();
            mOnItemChildClickListenerMaps.put(listener, idList);
        }
        for (int viewId : viewIds) {
            View view = getView(viewId);
            if (null != view) {
                idList.add(viewId);
                view.setOnClickListener(this);
            }
        }
        return this;
    }

    /**
     * 清空view容器
     */
    @Override
    public void clear() {
        if (null != mViews)
            mViews.clear();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == itemView) {
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemClick(v);
            }
        } else {
            if (null != mOnItemChildClickListenerMaps) {
                Set<Map.Entry<OnItemChildClickListener, List<Integer>>> entries = mOnItemChildClickListenerMaps.entrySet();
                if (null != entries && !entries.isEmpty()) {
                    for (Map.Entry<OnItemChildClickListener, List<Integer>> entry : entries) {
                        if (null != entry) {
                            List<Integer> value = entry.getValue();
                            if (null != value && value.contains(v.getId())) {
                                OnItemChildClickListener key = entry.getKey();
                                if (null != key) {
                                    key.onItemChildClick(v);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
