package com.orange.thirdparty.retrofit.zip.function;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.thirdparty.retrofit.zip.response.count2.ZipData2;

import io.reactivex.functions.BiFunction;

public class Zip2Function<T1, T2> implements BiFunction<BaseResponse<T1>, BaseResponse<T2>, ZipData2<T1, T2>> {
    @Override
    public ZipData2<T1, T2> apply(BaseResponse<T1> t1, BaseResponse<T2> t2) throws Exception {
        return new ZipData2<T1, T2>(t1, t2);
    }
}
