package com.orange.thirdparty.retrofit.subscriber;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.retrofit.consumer.CommonConsumer;
import com.orange.utils.common.Commons;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TSubscriber {

    public static TSubscriber newInstance() {
        return new TSubscriber();
    }

    public Disposable subscribe(Observable observable, ICallback netCallback) {
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
        return new Consumer<BaseResponse<T>>() {
            @Override
            public void accept(BaseResponse<T> result) {
                if (null != netCallback && null != result) {
                    int code = result.code;
                    if (Commons.checkCodeSuccess(code)) {
                        netCallback.onSuccess(null != result ? result.data : null);
                    } else {
                        netCallback.onError(code, new Throwable(result.msg));
                    }
                }
            }
        };
    }
}
