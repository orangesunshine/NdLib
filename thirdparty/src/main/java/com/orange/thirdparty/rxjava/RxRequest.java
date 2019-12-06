package com.orange.thirdparty.rxjava;


import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.params.SerialRxParams;
import com.orange.thirdparty.rxjava.parse.ResponseBodyParser;
import com.orange.thirdparty.rxjava.subscriber.RxSubscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RxRequest {
    private Observable mObservable;
    private RxParams mRxParams;

    private RxRequest(RxParams rxParams) {
        if (null != rxParams) {
            mRxParams = rxParams;
            mObservable = rxParams.generateObservable();
        }
    }

    public static RxRequest newInstance(RxParams rxParams) {
        return new RxRequest(rxParams);
    }

    public RxRequest serial(SerialRxParams serialRxParams) {
        if (null == mObservable || null == serialRxParams || null == mRxParams) return null;
        mObservable = mObservable
                .observeOn(Schedulers.io())
                .flatMap(new io.reactivex.functions.Function<ResponseBody, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(ResponseBody responseBody) {
                        if (null != mRxParams) {
                            ResponseBodyParser responseBodyParser = new ResponseBodyParser(responseBody, mRxParams.getType());
                            BaseResponse response = responseBodyParser.parseResponse();
                            serialRxParams.flatMapConvert(response);
                            mRxParams = serialRxParams;
                            return serialRxParams.generateObservable();
                        }
                        return null;
                    }
                });
        return this;
    }

    public <T> RxRequest parallel(RxParams rxParams, BiFunction biFunction) {
        Observable<ResponseBody> observable;
        if (null == mObservable || null == rxParams || null == (observable = rxParams.generateObservable()))
            throw new IllegalArgumentException("parallel arg error");
        mObservable = Observable.zip(mObservable, observable, biFunction);
        return this;
    }

    public <T> RxRequest parallel(RxParams rxParams1, RxParams rxParams2, Function3 function3) {
        Observable<ResponseBody> observable1;
        Observable<ResponseBody> observable2;
        if (null == mObservable ||
                null == rxParams1 || null == (observable1 = rxParams1.generateObservable()) ||
                null == rxParams2 || null == (observable2 = rxParams2.generateObservable()))
            throw new IllegalArgumentException("parallel arg error");
        mObservable = Observable.zip(mObservable, observable1, observable2, function3);
        return this;
    }

    public <T> RxRequest parallel(RxParams rxParams1, RxParams rxParams2, RxParams rxParams3, Function4 function4) {
        Observable<ResponseBody> observable1;
        Observable<ResponseBody> observable2;
        Observable<ResponseBody> observable3;
        if (null == mObservable ||
                null == rxParams1 || null == (observable1 = rxParams1.generateObservable()) ||
                null == rxParams2 || null == (observable2 = rxParams2.generateObservable()) ||
                null == rxParams3 || null == (observable3 = rxParams3.generateObservable()))
            throw new IllegalArgumentException("parallel arg error");
        mObservable = Observable.zip(mObservable, observable1, observable2, observable3, function4);
        return this;
    }

    public <T> Disposable subcribe(ICallback<T> callback) {
        if (null == mObservable) throw new NullPointerException("null == mObservable");
        return RxSubscriber.getInstance().subscribe(mObservable, callback);
    }
}
