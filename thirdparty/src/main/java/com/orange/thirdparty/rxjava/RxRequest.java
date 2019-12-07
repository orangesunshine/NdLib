package com.orange.thirdparty.rxjava;


import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.parse.FlatMapConvert;
import com.orange.thirdparty.rxjava.parse.RxParser;
import com.orange.thirdparty.rxjava.subscriber.RxSubscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RxRequest {
    private Observable mObservable;
    private RxParams mPreRxParams;

    private RxRequest(RxParams preRxParams) {
        if (null != preRxParams) {
            mPreRxParams = preRxParams;
            mObservable = preRxParams.generateObservable();
        }
    }

    public static RxRequest newInstance(RxParams rxParams) {
        return new RxRequest(rxParams);
    }

    /**
     * 用于没有调用过parallel，参数为ResponseBody类型
     *
     * @param params
     * @param flatMapConvert
     * @param <T>
     * @return
     */
    public <T> RxRequest serialResponseBody(RxParams params, FlatMapConvert<BaseResponse<T>> flatMapConvert) {
        if (null == mObservable || null == params || null == mPreRxParams) return null;
        mObservable = mObservable
                .observeOn(Schedulers.io())
                .flatMap(new io.reactivex.functions.Function<ResponseBody, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(ResponseBody response) {
                        if (null != mPreRxParams) {
                            if (null != flatMapConvert) {
                                flatMapConvert.convert(RxParser.parse(response, mPreRxParams.getType()), params);
                            }
                            mPreRxParams = params;
                            return params.generateObservable();
                        }
                        return null;
                    }
                });
        return this;
    }

    public <T> RxRequest serial(RxParams params, FlatMapConvert<T> flatMapConvert) {
        if (null == mObservable || null == params || null == mPreRxParams) return null;
        mObservable = mObservable
                .observeOn(Schedulers.io())
                .flatMap(new io.reactivex.functions.Function<T, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(T result) {
                        if (null != mPreRxParams) {
                            if (null != flatMapConvert)
                                flatMapConvert.convert(result, params);
                            mPreRxParams = params;
                            return params.generateObservable();
                        }
                        return null;
                    }
                });
        return this;
    }

    public <T1, T2, R> RxRequest parallel(RxParams rxParams, BiFunction<BaseResponse<T1>, BaseResponse<T2>, R> biFunction) {
        Observable<ResponseBody> observable;
        if (null == mObservable || null == rxParams || null == (observable = rxParams.generateObservable()))
            throw new IllegalArgumentException("parallel arg error");
        mObservable = Observable.zip(mObservable.subscribeOn(Schedulers.io()), observable.subscribeOn(Schedulers.io()), new BiFunction<ResponseBody, ResponseBody, R>() {
            @Override
            public R apply(ResponseBody responseBody, ResponseBody responseBody1) throws Exception {
                System.out.println("THREAD-parallel: " + Thread.currentThread().getName());
                return biFunction.apply(RxParser.parse(responseBody, mPreRxParams), RxParser.parse(responseBody1, rxParams));
            }
        });
        return this;
    }

    public <T1, T2, T3, R> RxRequest parallel(RxParams rxParams1, RxParams rxParams2, Function3<BaseResponse<T1>, BaseResponse<T2>, BaseResponse<T3>, R> function3) {
        Observable<ResponseBody> observable1;
        Observable<ResponseBody> observable2;
        if (null == mObservable ||
                null == rxParams1 || null == (observable1 = rxParams1.generateObservable()) ||
                null == rxParams2 || null == (observable2 = rxParams2.generateObservable()))
            throw new IllegalArgumentException("parallel arg error");
        mObservable = Observable.zip(mObservable.subscribeOn(Schedulers.io()),
                observable1.subscribeOn(Schedulers.io()),
                observable2.subscribeOn(Schedulers.io()),
                new Function3<ResponseBody, ResponseBody, ResponseBody, R>() {
                    @Override
                    public R apply(ResponseBody responseBody, ResponseBody responseBody1, ResponseBody responseBody2) throws Exception {
                        return function3.apply(RxParser.parse(responseBody, mPreRxParams), RxParser.parse(responseBody1, rxParams1), RxParser.parse(responseBody2, rxParams2));
                    }
                });
        return this;
    }

    public <T1, T2, T3, T4, R> RxRequest parallel(RxParams rxParams1, RxParams rxParams2, RxParams rxParams3, Function4<BaseResponse<T1>, BaseResponse<T2>, BaseResponse<T3>, BaseResponse<T4>, R> function4) {
        Observable<ResponseBody> observable1;
        Observable<ResponseBody> observable2;
        Observable<ResponseBody> observable3;
        if (null == mObservable ||
                null == rxParams1 || null == (observable1 = rxParams1.generateObservable()) ||
                null == rxParams2 || null == (observable2 = rxParams2.generateObservable()) ||
                null == rxParams3 || null == (observable3 = rxParams3.generateObservable()))
            throw new IllegalArgumentException("parallel arg error");
        mObservable = Observable.zip(mObservable.subscribeOn(Schedulers.io()),
                observable1.subscribeOn(Schedulers.io()),
                observable2.subscribeOn(Schedulers.io()),
                observable3.subscribeOn(Schedulers.io()),
                new Function4<ResponseBody, ResponseBody, ResponseBody, ResponseBody, R>() {
                    @Override
                    public R apply(ResponseBody responseBody, ResponseBody responseBody1, ResponseBody responseBody2, ResponseBody responseBody3) throws Exception {
                        return function4.apply(RxParser.parse(responseBody, mPreRxParams), RxParser.parse(responseBody1, rxParams1), RxParser.parse(responseBody2, rxParams2), RxParser.parse(responseBody3, rxParams3));
                    }
                });
        return this;
    }

    public <T> RxNetCancel subcribe(ICallback<T> callback) {
        if (null == mObservable) throw new NullPointerException("null == mObservable");
        RxNetCancel rxNetCancel = new RxNetCancel(RxSubscriber.getInstance().subscribe(mObservable, callback));
        if (null != callback) callback.setINetCancel(rxNetCancel);
        return rxNetCancel;
    }

    public <T> RxNetCancel zipSubcribe(ICallback<T> callback) {
        if (null == mObservable) throw new NullPointerException("null == mObservable");
        RxNetCancel rxNetCancel = new RxNetCancel(RxSubscriber.getInstance().zipSubscribe(mObservable, callback));
        if (null != callback) callback.setINetCancel(rxNetCancel);
        return rxNetCancel;
    }
}
