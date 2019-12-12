package com.orange.thirdparty.rxjava.params.generate;

import com.orange.thirdparty.rxjava.params.RxParams;

import io.reactivex.Observable;

public interface IGenerateObservable<T> {
    Observable<T> getObservable();

    RxParams addParams(String key, String value);
}
