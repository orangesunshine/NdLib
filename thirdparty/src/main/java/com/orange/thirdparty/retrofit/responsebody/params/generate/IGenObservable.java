package com.orange.thirdparty.retrofit.responsebody.params.generate;

import com.orange.thirdparty.retrofit.responsebody.params.RbParams;

import io.reactivex.Observable;

public interface IGenObservable<T> {
    Observable<T> getObservable();

    RbParams addParams(String key, String value);
}
