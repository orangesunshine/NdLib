package com.orange.thirdparty.rxjava;


import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.mvp.model.net.callback.loading.OnCompleteListener;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.rxjava.params.RxParams;
import com.orange.thirdparty.rxjava.params.RxSerialParams;
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
import okhttp3.ResponseBody;

/**
 * 单个、串行、并行（2,3,4）网络请求
 */
public class RxRequest {
    private List<INetCancel> mNetCancels = new ArrayList<>();
    private Observable mObservable;

    private RxRequest(Observable observable) {
        mObservable = Preconditions.needNotNull(observable);
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
     * 串行访问网络
     *
     * @param params 用于动态添加参数，生成Observable
     * @return
     */
    public RxRequest serial(RxSerialParams<ResponseBody> params) {
        if (Preconditions.isNulls(mObservable, params, params.getFlatMapConvert())) return this;
        mObservable = serial(mObservable, params, new FlatMapConvert<ResponseBody>() {
            @Override
            public void convert(ResponseBody response, RxParams rxSerialParams) {
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
        if (Preconditions.isNulls(mObservable, observable, biFunction)) return this;
        mObservable = Observable.zip(mObservable.subscribeOn(Schedulers.io()), observable.subscribeOn(Schedulers.io()), biFunction);
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
        if (Preconditions.isNulls(mObservable, observable2, observable3, function3)) return this;
        mObservable = Observable.zip(mObservable.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), observable3.subscribeOn(Schedulers.io()), function3);
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
        if (Preconditions.isNulls(mObservable, observable2, observable3, observable4, function4))
            return this;
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
        RxNetCancel rxNetCancel = new RxNetCancel(RxSubscriber.getInstance().subscribe(mObservable, callback));
        mNetCancels.add(rxNetCancel);
        if (null != callback)
            callback.setOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete() {
                    if (!Preconditions.isEmpty(mNetCancels))
                        mNetCancels.remove(rxNetCancel);
                }
            });
        return rxNetCancel;
    }

    ///////////////////////////////////////////////////////////////////////////
    // tools工具
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 串行访问网络
     *
     * @param params
     * @param <T>
     * @return
     */
    public <T> Observable<ResponseBody> serial(Observable<BaseResponse<T>> observable, RxSerialParams params) {
        Preconditions.needNotNull(observable, params);
        return observable.observeOn(Schedulers.io())
                .flatMap(new io.reactivex.functions.Function<BaseResponse<T>, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(BaseResponse<T> baseResponse) {
                        if (null != params) params.flatMapConvert(baseResponse);
                        return Preconditions.needNotNull(params).getObservable();
                    }
                });
    }

    /**
     * 串行访问网络
     *
     * @param params
     * @param convert
     * @param <T>
     * @return
     */
    public <T> Observable<ResponseBody> serial(Observable<T> observable, RxParams params, FlatMapConvert<T> convert) {
        Preconditions.needNotNull(observable, params);
        return observable.observeOn(Schedulers.io())
                .flatMap(new io.reactivex.functions.Function<T, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(T t) {
                        if (null != convert) convert.convert(t, params);
                        return Preconditions.needNotNull(params).getObservable();
                    }
                });
    }

    /**
     * 并行访问网络-2个
     *
     * @param observable1 网络1
     * @param observable2 网络2
     * @param biFunction  回调
     * @param <T1>        网络1解析
     * @param <T2>        网络1解析
     * @param <R>         合并后返回
     * @return
     */
    public <T1, T2, R> void parallel(Observable<T1> observable1, Observable<T2> observable2, BiFunction<T1, T2, R> biFunction) {
        if (Preconditions.isNulls(observable1, observable2, biFunction)) return;
        Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), biFunction);
    }

    /**
     * 并行访问网络-3个
     *
     * @param observable1 网络1
     * @param observable2 网络2
     * @param observable3 网络3
     * @param function3   回调
     * @param <T1>        网络1解析
     * @param <T2>        网络1解析
     * @param <T3>        网络1解析
     * @param <R>         合并后返回
     * @return
     */
    public <T1, T2, T3, R> void parallel(Observable<T1> observable1, Observable<T2> observable2, Observable<T3> observable3, Function3<T1, T2, T3, R> function3) {
        if (Preconditions.isNulls(observable1, observable2, observable3, function3)) return;
        Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), observable3.subscribeOn(Schedulers.io()), function3);
    }

    /**
     * 并行访问网络-4个
     *
     * @param observable1 网络1
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
     */
    public <T1, T2, T3, T4, R> void parallel(Observable<T1> observable1, Observable<T2> observable2, Observable<T3> observable3, Observable<T4> observable4, Function4<T1, T2, T3, T4, R> function4) {
        if (Preconditions.isNulls(observable1, observable2, observable3, observable4, function4))
            return;
        Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), observable3.subscribeOn(Schedulers.io()), observable4.subscribeOn(Schedulers.io()), function4);
    }

    /**
     * 订阅网络（单个）
     *
     * @param observable 网络数据
     * @param callback   网络回调
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subcribe(Observable<T> observable, ICallback<T> callback) {
        RxNetCancel rxNetCancel = new RxNetCancel(RxSubscriber.getInstance().subscribe(observable, callback));
        mNetCancels.add(rxNetCancel);
        if (null != callback)
            callback.setOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete() {
                    if (!Preconditions.isEmpty(mNetCancels))
                        mNetCancels.remove(rxNetCancel);
                }
            });
        return rxNetCancel;
    }
}
