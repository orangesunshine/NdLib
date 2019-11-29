package com.orange.thirdparty.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IRetrofitApi {
    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////
    // <editor-fold defaultstate="collapsed" desc="二级路径">
    @GET("/{prefix}/{suffix}")
    Observable<ResponseBody> get(@Path("prefix") String prefix, @Path("suffix") String suffix);

    @GET("/{prefix}/{suffix}")
    Observable<ResponseBody> get(@Path("prefix") String prefix, @Path("suffix") String suffix, @QueryMap Map<String, String> params);

    @GET("/{prefix}/{suffix}")
    Observable<ResponseBody> get(@HeaderMap Map<String, String> headers, @Path("prefix") String prefix, @Path("suffix") String suffix, @QueryMap Map<String, String> params);
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="全路径">

    /**
     * 不带参
     *
     * @param url
     * @return
     */
    @GET("")
    Observable<ResponseBody> get(@Url String url);

    /**
     * 带参
     *
     * @param url
     * @param params
     * @return
     */
    @GET("")
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> params);

    /**
     * 带参、带请求头
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    @GET("")
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> params, @HeaderMap Map<String, String> headers);
// </editor-fold>

    ///////////////////////////////////////////////////////////////////////////
    // POST
    ///////////////////////////////////////////////////////////////////////////

    // <editor-fold defaultstate="collapsed" desc="二级路径">

    /**
     * 不带参
     *
     * @param prefix
     * @param suffix
     * @return
     */
    @POST("/{prefix}/{suffix}")
    Observable<ResponseBody> post(@Path("prefix") String prefix, @Path("suffix") String suffix);

    /**
     * 带参
     *
     * @param prefix 一级路径
     * @param suffix 二级路径
     * @param params 参数
     * @return
     */
    @FormUrlEncoded
    @POST("/{prefix}/{suffix}")
    Observable<ResponseBody> post(@Path("prefix") String prefix, @Path("suffix") String suffix, @FieldMap Map<String, String> params);

    /**
     * 带参、带请求头
     *
     * @param prefix
     * @param suffix
     * @param params  参数
     * @param headers 请求头
     * @return
     */
    @FormUrlEncoded
    @POST("/{prefix}/{suffix}")
    Observable<ResponseBody> post(@Path("prefix") String prefix, @Path("suffix") String suffix, @FieldMap Map<String, String> params, @HeaderMap Map<String, String> headers);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="全路径">

    /**
     * 不带参
     *
     * @param url
     * @return
     */
    @POST()
    Observable<ResponseBody> post(@Url String url);

    /**
     * 带参
     *
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> params);

    /**
     * 带参、带请求头
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> params, @HeaderMap Map<String, String> headers);
    // </editor-fold>
}
