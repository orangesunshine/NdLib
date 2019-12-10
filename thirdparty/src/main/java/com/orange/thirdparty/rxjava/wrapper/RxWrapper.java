package com.orange.thirdparty.rxjava.wrapper;

import io.reactivex.Observable;

public class RxWrapper<T> {
    private Observable<T> mObservable;

    public RxWrapper(Observable<T> observable) {
        mObservable = observable;
    }


}
