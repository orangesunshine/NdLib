package com.orange.ndlib.activity.demo.retrofit;


import com.orange.lib.common.reponse.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitDemoApi {
    @POST("retrofit/{path}")
    @FormUrlEncoded
    Observable<BaseResponse<String>> getRetrofitFirstDemo(@Path("path") String path, @Field("request") String request);

    @POST("retrofit/{path}")
    @FormUrlEncoded
    Observable<BaseResponse<String>> getRetrofitSecondDemo(@Path("path") String path, @Field("request") String request);
}
