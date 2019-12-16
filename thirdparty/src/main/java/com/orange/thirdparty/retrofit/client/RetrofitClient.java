package com.orange.thirdparty.retrofit.client;

import android.text.TextUtils;

import com.orange.lib.constance.IConst;
import com.orange.lib.constance.IInitConst;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private volatile static Retrofit sInstance;

    public static Retrofit getRetrofitInstance() {
        if (null == sInstance) {
            synchronized (RetrofitClient.class) {
                if (null == sInstance) {
                    Retrofit.Builder client = new Retrofit.Builder()
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(newOkHttpClicentInstance());
                    String baseUrl = "";
                    String initBaseUrl = IInitConst.sBaseUrl;
                    if (TextUtils.isEmpty(baseUrl) && !TextUtils.isEmpty(initBaseUrl))
                        baseUrl = initBaseUrl;

                    String finalBaseUrl = IInitConst.sBaseUrl;
                    if (TextUtils.isEmpty(baseUrl) && !TextUtils.isEmpty(finalBaseUrl))
                        baseUrl = finalBaseUrl;
                    if (TextUtils.isEmpty(baseUrl))
                        baseUrl = IConst.sBaseUrl;
                    client.baseUrl(baseUrl);
                    sInstance = client.build();
                }
            }
        }
        return sInstance;
    }

    private RetrofitClient() {

    }

    private static OkHttpClient newOkHttpClicentInstance() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }
}
