package com.orange.lib.component.recyclerview;

public interface IConvertRecyclerView<T> {
    /**
     * 对外提供的方法
     */
    void convert(RecyclerViewHolder holder, T t, boolean selected);
}
