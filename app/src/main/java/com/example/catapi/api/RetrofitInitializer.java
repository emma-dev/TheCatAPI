package com.example.catapi.api;

import com.android.volley.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitializer {
    private Retrofit retrofit;
    private static final String BASE_URL = "https://api.thecatapi.com/";
    private static final String TOKEN_KEY = "x-api-key";
    private static final String TOKEN_VALUE = "9b7e282d-2a67-4c7b-a9fd-3f3e4056e949";
    public static final int TIME_OUT_CONNECTION =10000;


    public RetrofitInitializer() {
        OkHttpClient client = this.createHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public CatApi catApi() {
        return retrofit.create(CatApi.class);
    }





    private OkHttpClient createHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        if (BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return (new OkHttpClient.Builder())
                .connectTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    request = request.newBuilder()
                            .addHeader(TOKEN_KEY, TOKEN_VALUE)
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .cache(null).build();


    }


}
