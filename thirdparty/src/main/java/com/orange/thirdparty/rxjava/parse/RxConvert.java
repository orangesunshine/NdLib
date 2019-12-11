package com.orange.thirdparty.rxjava.parse;

import com.orange.thirdparty.retrofit.RetrofitConvert;

import okhttp3.ResponseBody;

public interface RxConvert<T> extends RetrofitConvert<T, ResponseBody> {
}
