package com.orange.thirdparty.rxjava.subscriber;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.retrofit.CommonConsumer;
import com.orange.thirdparty.rxjava.parse.RxParser;
import com.orange.utils.common.Commons;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RxSubscriber {
    public static RxSubscriber newInstance() {
        return new RxSubscriber();
    }

    public <T> Disposable subscribe(Observable<ResponseBody> observable, ICallback<T> netCallback) {
        Preconditions.needNotNull(observable);
        CommonConsumer commonConsumer = new CommonConsumer(netCallback);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nextConsumer(netCallback),
                        commonConsumer.errorConsumer(),
                        commonConsumer.completeConsumer(),
                        commonConsumer.onSubcribe());
    }

    public <T> Disposable subscribe(Observable<ResponseBody> observable, ICallback<T> netCallback, Type type) {
        Preconditions.needNotNull(observable);
        CommonConsumer commonConsumer = new CommonConsumer(netCallback);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nextConsumer(netCallback, type),
                        commonConsumer.errorConsumer(),
                        commonConsumer.completeConsumer(),
                        commonConsumer.onSubcribe());
    }

    protected <T> Consumer nextConsumer(ICallback<T> netCallback, Type type) {
        return new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) {
                BaseResponse<T> result = RxParser.parse(responseBody, type);
                if (null == result)
                    result = RxParser.parse(responseBody, netCallback);
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

    /**
     * @param netCallback
     * @param <R>
     * @return
     */
    protected <R> Consumer nextConsumer(ICallback<R> netCallback) {
        return new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) {
                BaseResponse<R> result = RxParser.parse(responseBody, netCallback);
                if (null == result)
                    result = RxParser.parse(responseBody, netCallback);
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
}
