package com.orange.thirdparty.retrofit;

import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Preconditions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RetrofitSubscriber<T> {

    public static RetrofitSubscriber newInstance() {
        return new RetrofitSubscriber();
    }

    public Disposable subscribe(Observable<T> observable, ICallback<T> netCallback) {
        Preconditions.needNotNull(observable);
        CommonConsumer commonConsumer = new CommonConsumer(netCallback);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nextConsumer(netCallback),
                        commonConsumer.errorConsumer(),
                        commonConsumer.completeConsumer(),
                        commonConsumer.onSubcribe());
    }

    protected <T> Consumer nextConsumer(ICallback<T> netCallback) {
        return new Consumer<T>() {
            @Override
            public void accept(T response) {
                if (null != netCallback)
                    netCallback.onSuccess(response);
            }
        };
    }
}
