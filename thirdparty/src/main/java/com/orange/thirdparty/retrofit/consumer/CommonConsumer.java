package com.orange.thirdparty.retrofit.consumer;

import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class CommonConsumer {
    private ICallback mICallback;

    public CommonConsumer(ICallback ICallback) {
        mICallback = ICallback;
    }

    public Consumer errorConsumer() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //响应数据onError
                if (null != mICallback) {
                    mICallback.onError(IConst.CODE_ERROR, throwable);
                    mICallback.onComplete();
                }
            }
        };
    }

    /**
     * //响应数据onComplete
     *
     * @return
     */
    public Action completeConsumer() {
        return new Action() {
            @Override
            public void run() throws Exception {
                if (null != mICallback)
                    mICallback.onComplete();
            }
        };
    }

    /**
     * 响应数据onSubcribe
     *
     * @return
     */
    public Consumer onSubcribe() {
        return new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                //响应数据onSubcribe
                if (null != mICallback)
                    mICallback.onStart();
            }
        };
    }
}
