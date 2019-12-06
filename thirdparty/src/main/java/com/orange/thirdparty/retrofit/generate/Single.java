package com.orange.thirdparty.retrofit.generate;

import com.orange.thirdparty.retrofit.params.RetrofitParams;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class Single {
    private RetrofitParams mRetrofitParams;

    public RetrofitParams getRetrofitParams() {
        return mRetrofitParams;
    }

    public void setRetrofitParams(RetrofitParams retrofitParams) {
        mRetrofitParams = retrofitParams;
    }

    public Observable<ResponseBody> generateSingleObservable() {
        if (null == mRetrofitParams) return null;
        return mRetrofitParams.generateObservable();
    }
}
