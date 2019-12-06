package com.orange.thirdparty.retrofit.generate;

import io.reactivex.Observable;

public interface IGenerate<T> {
    /**
     * 生成网络主题
     *
     * @return
     */
    Observable<T> generateObservable();
}
