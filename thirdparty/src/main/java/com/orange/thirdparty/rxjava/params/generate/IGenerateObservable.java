package com.orange.thirdparty.rxjava.params.generate;

import com.orange.thirdparty.rxjava.params.RxParams;

import java.lang.reflect.Type;

import io.reactivex.Observable;

public interface IGenerateObservable<T> {
    Observable<T> getObservable(Type type);

    Observable<T> getObservable();

    RxParams addParams(String key, String value);
}
