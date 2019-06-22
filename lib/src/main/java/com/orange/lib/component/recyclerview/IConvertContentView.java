package com.orange.lib.component.recyclerview;


import com.orange.lib.common.holder.IHolder;

public interface IConvertContentView<T> {
    /**
     * 对外提供的方法
     */
    void convert(IHolder holder, T t);
}
