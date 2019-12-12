package com.orange.thirdparty.retrofit;

import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.mvp.model.net.callback.loading.OnCompleteListener;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.rxjava.RxNetCancel;
import com.orange.thirdparty.rxjava.params.generate.IGenerateObservable;
import com.orange.thirdparty.rxjava.parse.RxMapper;
import com.orange.thirdparty.rxjava.subscriber.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RetrofitRequest {
    private List<INetCancel> mNetCancels = new ArrayList<>();
    private Observable mObservable;

    // <editor-fold defaultstate="collapsed" desc="多例">

    public RetrofitRequest(Observable observable) {
        mObservable = observable;
    }

    public static RetrofitRequest newInstance(Observable observable) {
        return new RetrofitRequest(observable);
    }

    // </editor-fold>

    /**
     * 串行访问网络，上一个接口返回ResponseBody
     *
     * @param generate 用于动态添加参数，生成Observable
     * @return
     */
    public <T, R> RetrofitRequest serial(IGenerateObservable<R> generate, RxMapper<T> convert) {
        mObservable = serial(mObservable, generate, convert);
        return this;
    }

    /**
     * 串行访问网络，上一个接口返回ResponseBody
     *
     * @param generate 用于动态添加参数，生成Observable
     * @return
     */
    public <T, R> RetrofitRequest serial(IGenerateObservable<R> generate, RetrofitMapper<T> convert) {
        mObservable = serial(mObservable, generate, convert);
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
    public <T1, T2, R> RetrofitRequest parallel(Observable<T2> observable, BiFunction<T1, T2, R> biFunction) {
        Preconditions.needNotNull(mObservable, observable, biFunction);
        mObservable = parallel(mObservable, observable, biFunction);
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
    public <T1, T2, T3, R> RetrofitRequest parallel(Observable<T2> observable2, Observable<T3> observable3, Function3<T1, T2, T3, R> function3) {
        Preconditions.needNotNull(mObservable, observable2, observable3, function3);
        mObservable = parallel(mObservable, observable2, observable3, function3);
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
    public <T1, T2, T3, T4, R> RetrofitRequest parallel(Observable<T2> observable2, Observable<T3> observable3, Observable<T4> observable4, Function4<T1, T2, T3, T4, R> function4) {
        Preconditions.needNotNull(mObservable, observable2, observable3, observable4, function4);
        mObservable = parallel(mObservable, observable2, observable3, observable4, function4);
        return this;
    }

    /**
     * 订阅网络（单个）
     *
     * @param callback 网络回调
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subscribe(ICallback<T> callback) {
        return subscribe(mObservable, callback);
    }

    /**
     * 订阅网络（单个）
     *
     * @param callback 网络回调
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subscribeResponseBody(ICallback<T> callback) {
        return subscribeResponseBody(mObservable, callback);
    }

    /**
     * 订阅网络（单个）
     *
     * @param callback 网络回调
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subscribe(RetrofitSubscriber retrofitSubscriber, ICallback<T> callback) {
        return subscribe(retrofitSubscriber, mObservable, callback);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 基础实现
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 串行访问网络
     *
     * @param observable
     * @return
     */
    public Observable serial(Observable observable, IGenerateObservable generate, Function function) {
        Preconditions.needNotNull(observable, generate);
        return observable.observeOn(Schedulers.io())
                .flatMap(function);
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
    public <T1, T2, R> Observable<R> parallel(Observable<T1> observable1, Observable<T2> observable2, BiFunction<T1, T2, R> biFunction) {
        Preconditions.needNotNull(observable1, observable2, biFunction);
        return Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), biFunction);
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
    public <T1, T2, T3, R> Observable<R> parallel(Observable<T1> observable1, Observable<T2> observable2, Observable<T3> observable3, Function3<T1, T2, T3, R> function3) {
        Preconditions.needNotNull(observable1, observable2, observable3, function3);
        return Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), observable3.subscribeOn(Schedulers.io()), function3);
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
    public <T1, T2, T3, T4, R> Observable<R> parallel(Observable<T1> observable1, Observable<T2> observable2, Observable<T3> observable3, Observable<T4> observable4, Function4<T1, T2, T3, T4, R> function4) {
        Preconditions.needNotNull(observable1, observable2, observable3, observable4, function4);
        return Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers.io()), observable3.subscribeOn(Schedulers.io()), observable4.subscribeOn(Schedulers.io()), function4);
    }

    /**
     * 订阅网络（单个）
     *
     * @param observable 网络数据
     * @param callback   网络回调
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subscribe(Observable<T> observable, ICallback<T> callback) {
        return subscribe(RetrofitSubscriber.newInstance(), observable, callback);
    }

    /**
     * 订阅网络（单个）- 网络返回类型为泛型
     *
     * @param observable 网络数据
     * @param callback   网络回调
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subscribe(RetrofitSubscriber retrofitSubscriber, Observable<T> observable, ICallback<T> callback) {
        Preconditions.needNotNull(observable, callback);
        RxNetCancel rxNetCancel = new RxNetCancel(Preconditions.needNotNull(retrofitSubscriber).subscribe(observable, callback));
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

    /**
     * 订阅网络（单个）- 网络返回类型为ResponseBody
     *
     * @param observable
     * @param callback
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subscribeResponseBody(Observable<ResponseBody> observable, ICallback<T> callback) {
        return subscribeResponseBody(RxSubscriber.newInstance(), observable, callback);
    }

    /**
     * 订阅网络（单个）- 网络返回类型为ResponseBody
     *
     * @param observable 网络数据
     * @param callback   网络回调
     * @param <T>
     * @return
     */
    public <T> RxNetCancel subscribeResponseBody(RxSubscriber rxSubscriber, Observable<ResponseBody> observable, ICallback<T> callback) {
        Preconditions.needNotNull(observable, callback);
        RxNetCancel rxNetCancel = new RxNetCancel(Preconditions.needNotNull(rxSubscriber).subscribe(observable, callback));
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
