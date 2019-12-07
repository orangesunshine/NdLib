package com.orange.thirdparty.rxjava.parse;

import com.orange.thirdparty.rxjava.params.RxParams;

public interface FlatMapConvert<T> {
    void convert(T response, RxParams params);
}
