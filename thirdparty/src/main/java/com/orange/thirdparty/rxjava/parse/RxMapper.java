package com.orange.thirdparty.rxjava.parse;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.rxjava.params.generate.IGenerateObservable;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public abstract class RxMapper<C> implements Function<ResponseBody, Observable> {
    private Type mPreType;//串行上一个网络返回类型
    protected IGenerateObservable generate;

    public void setGenerate(IGenerateObservable generate) {
        this.generate = generate;
    }

    public RxMapper(Type type) {
        mPreType = Preconditions.needNotNull(type);
    }

    @Override
    public Observable apply(ResponseBody responseBody) throws Exception {
        convert(RxParser.getInstance().parseResponse(responseBody, mPreType), generate);
        return Preconditions.needNotNull(generate).getObservable();
    }

    public abstract void convert(BaseResponse<C> t, IGenerateObservable generate);
}
