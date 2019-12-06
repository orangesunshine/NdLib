package com.orange.thirdparty.rxjava.parse;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.thirdparty.retrofit.params.RetrofitParams;

public interface FlatMapConvert<T> {
    void convert(BaseResponse<T> response, RetrofitParams params);
}
