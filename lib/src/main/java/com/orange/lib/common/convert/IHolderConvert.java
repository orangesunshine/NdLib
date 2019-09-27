package com.orange.lib.common.convert;

import com.orange.lib.common.holder.IHolder;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 10:05
 */
public interface IHolderConvert<T> {
    void convert(IHolder holder, T data);
}
