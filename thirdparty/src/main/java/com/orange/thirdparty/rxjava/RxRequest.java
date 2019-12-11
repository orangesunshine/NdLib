package com.orange.thirdparty.rxjava;


import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.retrofit.RetrofitRequest;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.params.RxSerialParams;
import com.orange.thirdparty.rxjava.parse.RxConvert;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 单个、串行、并行（2,3,4）网络请求
 */
public class RxRequest extends RetrofitRequest {

    public RxRequest(Observable observable) {
        super(observable);
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
        return newInstance(Preconditions.needNotNull(rxParams).getObservable());
    }

    /**
     * 串行访问网络，上一个接口返回ResponseBody
     *
     * @param params 用于动态添加参数，生成Observable
     * @return
     */
    public RxRequest serial(RxSerialParams params) {
        Preconditions.needNotNull(params, params.getRxConvert());
        mRetrofitRequest.serial(params, params.getRxConvert());
        return this;
    }

    /**
     * 串行访问网络
     *
     * @param params 用于动态添加参数，生成Observable
     * @return
     */
    public RxRequest serial(RxSerialParams<ResponseBody> params) {
        Preconditions.needNotNull(mObservable, params, params.getRxConvert());
        mObservable = mRetrofitRequest.serial(mObservable, params, new RxConvert<ResponseBody>() {
            @Override
            public void convert(ResponseBody response, RxParams rxParams) {
                if (null != params) params.flatMapConvert(response);
            }
        });
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
        Preconditions.needNotNull(mObservable, observable, biFunction);
        mObservable = mRetrofitRequest.parallel(mObservable, observable, biFunction);
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
        Preconditions.needNotNull(mObservable, observable2, observable3, function3);
        mObservable = mRetrofitRequest.parallel(mObservable, observable2, observable3, function3);
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
        Preconditions.needNotNull(mObservable, observable2, observable3, observable4, function4);
        mObservable = Observable.zip(mObservable.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), observable3.subscribeOn(Schedulers.io()), observable4.subscribeOn(Schedulers.io()), function4);
        return this;
    }

    /**
     * 订阅网络（单个）
     *
     * @param callback 网络回调
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subcribe(ICallback<T> callback) {
        return mRetrofitRequest.subcribe(mObservable, callback);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 基础方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 串行访问网络
     *
     * @param observable
     * @param params
     * @param <T>
     * @return
     */
    public <T> Observable<ResponseBody> serial(Observable<T> observable, RxSerialParams params) {
        Preconditions.needNotNull(observable, params);
        return mRetrofitRequest.serial(observable, params, params.getRxConvert());
    }

    /**
     * 串行访问网络
     *
     * @param params
     * @param convert
     * @param <T>
     * @return
     */
    public <T> Observable<ResponseBody> serial(Observable<T> observable, RxParams params, RxConvert<T> convert) {
        return mRetrofitRequest.serial(observable, params, convert);
    }
}
