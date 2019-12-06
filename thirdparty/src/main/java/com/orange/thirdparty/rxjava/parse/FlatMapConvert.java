package com.orange.thirdparty.rxjava.parse;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.thirdparty.rxjava.params.RxParams;

public interface FlatMapConvert<T> {
    void convert(BaseResponse<T> response, RxParams params);
}
