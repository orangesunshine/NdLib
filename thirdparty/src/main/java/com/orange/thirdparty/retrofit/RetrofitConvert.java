package com.orange.thirdparty.retrofit;

import com.orange.thirdparty.rxjava.params.generate.IGenerateObservable;

public interface RetrofitConvert<T1, T2> {
    void convert(T1 t1, IGenerateObservable<T2> generate);
}
