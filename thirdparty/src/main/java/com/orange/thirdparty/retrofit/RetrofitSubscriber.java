package com.orange.thirdparty.retrofit;

import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Preconditions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RetrofitSubscriber {

    public static RetrofitSubscriber newInstance() {
        return new RetrofitSubscriber();
    }

    private <T> Disposable subscribe(Observable<T> observable, ICallback<T> netCallback) {
        if (Preconditions.isNull(observable)) return null;
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nextConsumer(netCallback),
                        errorConsumer(netCallback),
                        completeConsumer(netCallback),
                        onSubcribe(netCallback));
    }

    private <T> Consumer nextConsumer(ICallback<T> netCallback) {
        return new Consumer<T>() {
            @Override
            public void accept(T response) {
                if (null != netCallback)
                    netCallback.onSuccess(response);
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
