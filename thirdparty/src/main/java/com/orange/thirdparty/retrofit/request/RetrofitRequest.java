package com.orange.thirdparty.retrofit.request;

import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.mvp.model.net.callback.loading.OnCompleteListener;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.retrofit.subscriber.TSubscriber;
import com.orange.thirdparty.retrofit.cancel.RetrofitNetCancel;
import com.orange.thirdparty.retrofit.responsebody.params.RbParams;
import com.orange.thirdparty.retrofit.responsebody.subscriber.RbSubscriber;

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

    public static RetrofitRequest newInstance(RbParams params) {
        return newInstance(Preconditions.needNotNull(params).getObservable());
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="serial">

    public RetrofitRequest serial(Function function) {
        mObservable = serial(mObservable, function);
        return this;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="parallel">

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
    public <T> RetrofitNetCancel subscribe(ICallback<T> callback) {
        return subscribe(mObservable, callback);
    }

    /**
     * 订阅网络（单个）
     *
     * @param callback 网络回调
     * @param <T>
     * @return
     */
    public <T> RetrofitNetCancel subscribeResponseBody(ICallback<T> callback) {
        return subscribeResponseBody(mObservable, callback);
    }

    /**
     * 订阅网络（单个）
     *
     * @param callback 网络回调
     * @param <T>
     * @return
     */
    public <T> RetrofitNetCancel subscribe(TSubscriber tSubscriber, ICallback<T> callback) {
        return subscribe(tSubscriber, mObservable, callback);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="基础实现">
    ///////////////////////////////////////////////////////////////////////////
    // 基础实现
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 串行访问网络
     *
     * @param observable
     * @return
     */
    public Observable serial(Observable observable, Function function) {
        Preconditions.needNotNull(observable);
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
    public <T> RetrofitNetCancel subscribe(Observable<T> observable, ICallback<T> callback) {
        return subscribe(TSubscriber.newInstance(), observable, callback);
    }

    /**
     * 订阅网络（单个）- 网络返回类型为泛型
     *
     * @param observable 网络数据
     * @param callback   网络回调
     * @param <T>
     * @return
     */
    public <T> RetrofitNetCancel subscribe(TSubscriber tSubscriber, Observable<T> observable, ICallback<T> callback) {
        Preconditions.needNotNull(observable, callback);
        RetrofitNetCancel retrofitNetCancel = new RetrofitNetCancel(Preconditions.needNotNull(tSubscriber).subscribe(observable, callback));
        mNetCancels.add(retrofitNetCancel);
        if (null != callback)
            callback.setOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete() {
                    if (!Preconditions.isEmpty(mNetCancels))
                        mNetCancels.remove(retrofitNetCancel);
                }
            });
        return retrofitNetCancel;
    }

    /**
     * 订阅网络（单个）- 网络返回类型为ResponseBody
     *
     * @param observable
     * @param callback
     * @param <T>
     * @return
     */
    public <T> RetrofitNetCancel subscribeResponseBody(Observable<ResponseBody> observable, ICallback<T> callback) {
        return subscribeResponseBody(RbSubscriber.newInstance(), observable, callback);
    }

    /**
     * 订阅网络（单个）- 网络返回类型为ResponseBody
     *
     * @param observable 网络数据
     * @param callback   网络回调
     * @param <T>
     * @return
     */
    public <T> RetrofitNetCancel subscribeResponseBody(RbSubscriber rbSubscriber, Observable<ResponseBody> observable, ICallback<T> callback) {
        Preconditions.needNotNull(observable, callback);
        RetrofitNetCancel retrofitNetCancel = new RetrofitNetCancel(Preconditions.needNotNull(rbSubscriber).subscribe(observable, callback));
        mNetCancels.add(retrofitNetCancel);
        if (null != callback)
            callback.setOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete() {
                    if (!Preconditions.isEmpty(mNetCancels))
                        mNetCancels.remove(retrofitNetCancel);
                }
            });
        return retrofitNetCancel;
    }
// </editor-fold>
}
