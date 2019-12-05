package com.orange.thirdparty.rxjava;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Commons;
import com.orange.lib.utils.Reflections;
import com.orange.lib.utils.base.Preconditions;
import com.orange.thirdparty.rxjava.parse.ResponseBodyParser;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class NetObserver {
    public static NetObserver newInstance() {
        return new NetObserver();
    }

    public <T> Disposable parallel(Observable<ResponseBody> observable1, Observable<ResponseBody> observable2, ICallback<T> netCallback, BiFunction<ResponseBody, ResponseBody, T> biFunction) {
        Type type = Reflections.getGenericActualTypeArg(netCallback.getClass());
        return subscribe(Observable.zip(observable1, observable2, biFunction), netCallback, type);
    }

    public <T> Disposable subResponseBody(Observable<ResponseBody> observable, ICallback<T> netCallback) {
        Type type = Reflections.getGenericActualTypeArg(netCallback.getClass());
        return subResponseBody(observable, netCallback, type);
    }

    private <T> Disposable subResponseBody(Observable<ResponseBody> observable, ICallback<T> netCallback, Type type) {
        if(Preconditions.isNull(observable))return null;
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nextConsumer(netCallback, type),
                        errorConsumer(netCallback),
                        completeConsumer(netCallback),
                        onSubcribe(netCallback));
    }

    private <T> Disposable subscribe(Observable<T> observable, ICallback<T> netCallback, Type type) {
        if(Preconditions.isNull(observable))return null;
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zipNextConsumer(netCallback),
                        errorConsumer(netCallback),
                        completeConsumer(netCallback),
                        onSubcribe(netCallback));
    }

    private <T> Consumer zipNextConsumer(ICallback<T> netCallback) {
        return new Consumer<BaseResponse<T>>() {
            @Override
            public void accept(BaseResponse<T> result) {
                if (null == result) return;
                if (null != netCallback) {
                    int code = result.code;
                    if (Commons.checkCodeSuccess(code)) {
                        netCallback.onSuccess(result.data);
                    } else {
                        netCallback.onError(code, new Throwable(result.msg));
                    }
                }
            }
        };
    }

    private <T> Consumer nextConsumer(ICallback<T> netCallback, Type type) {
        return new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) {
                BaseResponse<T> result = new ResponseBodyParser(responseBody, type).parseResponse();
                if (null == result)
                    result = new ResponseBodyParser(responseBody, netCallback).parseResponse();
                if (null == result) return;
                if (null != netCallback) {
                    int code = result.code;
                    if (Commons.checkCodeSuccess(code)) {
                        netCallback.onSuccess(result.data);
                    } else {
                        netCallback.onError(code, new Throwable(result.msg));
                    }
                }
            }
        };
    }

    private Consumer errorConsumer(ICallback netCallback) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //响应数据onError
                if (null != netCallback) {
                    netCallback.onError(IConst.CODE_ERROR, throwable);
                    netCallback.onComplete();
                }
            }
        };
    }

    /**
     * //响应数据onComplete
     *
     * @param netCallback
     * @return
     */
    private Action completeConsumer(ICallback netCallback) {
        return new Action() {
            @Override
            public void run() throws Exception {
                if (null != netCallback)
                    netCallback.onComplete();
            }
        };
    }

    /**
     * 响应数据onSubcribe
     *
     * @param netCallback
     * @return
     */
    private Consumer onSubcribe(ICallback netCallback) {
        return new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                //响应数据onSubcribe
                if (null != netCallback)
                    netCallback.onStart();
            }
        };
    }
}
