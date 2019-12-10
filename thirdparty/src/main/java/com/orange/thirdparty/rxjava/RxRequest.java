package com.orange.thirdparty.rxjava;


import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.parse.FlatMapConvert;
import com.orange.thirdparty.rxjava.subscriber.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;

public class RxRequest {
    private List<INetCancel> mNetCancels = new ArrayList<>();

    private RxRequest() {
    }

    public static RxRequest newInstance(RxParams rxParams) {
        return new RxRequest();
    }

    public <T1, T2> RxRequest serial(Observable<T1> observable1, Observable<T2> observable2, FlatMapConvert<T1> convert) {
        if (Preconditions.isNulls(observable1, observable2)) return this;
        observable1.observeOn(Schedulers.io())
                .flatMap(new io.reactivex.functions.Function<T1, ObservableSource<T2>>() {
                    @Override
                    public ObservableSource<T2> apply(T1 t1) {
                        if (null != convert) convert.convert(t1);
                        return observable2;
                    }
                });
        return this;
    }

    public <T1, T2, R> RxRequest parallel(Observable<T1> observable1, Observable<T2> observable2, BiFunction<T1, T2, R> biFunction) {
        if (Preconditions.isNulls(observable1, observable2, biFunction)) return this;
        Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), biFunction);
        return this;
    }

    public <T1, T2, T3, R> RxRequest parallel(Observable<T1> observable1, Observable<T2> observable2, Observable<T3> observable3, Function3<T1, T2, T3, R> function3) {
        if (Preconditions.isNulls(observable1, observable2, observable3, function3)) return this;
        Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), observable3.subscribeOn(Schedulers.io()), function3);
        return this;
    }

    public <T1, T2, T3, T4, R> RxRequest parallel(Observable<T1> observable1, Observable<T2> observable2, Observable<T3> observable3, Observable<T4> observable4, Function4<T1, T2, T3, T4, R> function4) {
        if (Preconditions.isNulls(observable1, observable2, observable3, observable4, function4))
            return this;
        Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), observable3.subscribeOn(Schedulers.io()), observable4.subscribeOn(Schedulers.io()), function4);
        return this;
    }

    public <T> RxNetCancel subcribe(Observable<T> observable, ICallback<T> callback) {
        RxNetCancel rxNetCancel = new RxNetCancel(RxSubscriber.getInstance().subscribe(observable, callback));
        return rxNetCancel;
    }
}
