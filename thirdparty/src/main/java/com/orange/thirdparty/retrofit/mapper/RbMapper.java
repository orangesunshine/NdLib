package com.orange.thirdparty.retrofit.mapper;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.utils.Reflections;
import com.orange.thirdparty.rxjava.parse.RxParser;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public abstract class RbMapper<T> implements Function<ResponseBody, Observable> {

    @Override
    public Observable apply(ResponseBody responseBody) throws Exception {
        return convert(RxParser.getInstance().parseResponse(responseBody, Reflections.getGenericActualTypeArg(getClass())));
    }

    public abstract Observable convert(BaseResponse<T> t);
}
