package com.orange.thirdparty.rxjava;


import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.retrofit.RetrofitRequest;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.params.RxSerialParams;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;

/**
 * 单个、串行、并行（2,3,4）网络请求
 */
public class RxRequest {
    RetrofitRequest mRetrofitRequest;

    public RxRequest(Observable observable) {
        mRetrofitRequest = RetrofitRequest.newInstance(observable);
    }

    public RxRequest(RxParams params) {
        this(Preconditions.needNotNull(params).getObservable());
    }

    /**
     * 多例
     *
     * @return
     */
    public static RxRequest newInstance(Observable observable) {
        return new RxRequest(observable);
    }

    /**
     * 多例
     *
     * @return
     */
    public static RxRequest newInstance(RxParams rxParams) {
        return new RxRequest(rxParams);
    }

    /**
     * 串行访问网络，上一个接口返回ResponseBody
     *
     * @param params 用于动态添加参数，生成Observable
     * @return
     */
    public RxRequest serial(RxSerialParams params) {
        Preconditions.needNotNull(params, params.getRxMapper());
        Preconditions.needNotNull(mRetrofitRequest).serial(params, params.getRxMapper());
        return this;
    }

    /**
     * 并行访问网络-2个
     *
     * @param observable 网络2
     * @param biFunction 回调
     * @param <T1>       网络1解析
     * @param <T2>       网络1解析
     * @param <R>        合并后返回
     * @return
     * @<code>mObservable</code> 网络1
     */
    public <T1, T2, R> RxRequest parallel(Observable<T2> observable, BiFunction<T1, T2, R> biFunction) {
        Preconditions.needNotNull(mRetrofitRequest).parallel(observable, biFunction);
        return this;
    }

    /**
     * 并行访问网络-3个
     *
     * @param observable2 网络2
     * @param observable3 网络3
     * @param function3   回调
     * @param <T1>        网络1解析
     * @param <T2>        网络1解析
     * @param <T3>        网络1解析
     * @param <R>         合并后返回
     * @return
     * @<code>mObservable</code> 网络1
     */
    public <T1, T2, T3, R> RxRequest parallel(Observable<T2> observable2, Observable<T3> observable3, Function3<T1, T2, T3, R> function3) {
        Preconditions.needNotNull(mRetrofitRequest).parallel(observable2, observable3, function3);
        return this;
    }

    /**
     * 并行访问网络-4个
     *
     * @param observable2 网络2
     * @param observable3 网络3
     * @param observable4 网络4
     * @param function4   回调
     * @param <T1>        网络1解析
     * @param <T2>        网络1解析
     * @param <T3>        网络1解析
     * @param <T4>        网络4解析
     * @param <R>         合并后返回
     * @return
     * @<code>mObservable</code> 网络1
     */
    public <T1, T2, T3, T4, R> RxRequest parallel(Observable<T2> observable2, Observable<T3> observable3, Observable<T4> observable4, Function4<T1, T2, T3, T4, R> function4) {
        Preconditions.needNotNull(mRetrofitRequest).parallel(observable2, observable3, observable4, function4);
        return this;
    }

    /**
     * 订阅网络（单个）
     *
     * @param callback 网络回调
     * @return
     */
    public <T> RxNetCancel subscribeResponseBody(ICallback<T> callback) {
        return Preconditions.needNotNull(mRetrofitRequest).subscribeResponseBody(callback);
    }

    /**
     * 订阅网络（单个）
     *
     * @param callback 网络回调
     * @return
     */
    public <T> RxNetCancel subscribe(ICallback<T> callback) {
        return Preconditions.needNotNull(mRetrofitRequest).subscribe(callback);
    }
}
