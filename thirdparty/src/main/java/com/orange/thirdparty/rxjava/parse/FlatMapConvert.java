package com.orange.thirdparty.rxjava.parse;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.request.request.Params;

public interface FlatMapConvert<T> {
    void convert(BaseResponse<T> response, Params params);
}
