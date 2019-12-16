package com.orange.thirdparty.retrofit.mapper;

import com.orange.lib.common.reponse.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public abstract class TMapper<T> implements Function<BaseResponse<T>, Observable> {
}
