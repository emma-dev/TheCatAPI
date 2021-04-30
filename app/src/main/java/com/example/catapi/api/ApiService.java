package com.example.catapi.api;

import android.util.Log;

import com.example.catapi.BuildConfig;
import com.example.catapi.modal.Cat;
import com.example.catapi.modal.CatsModal;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static OkHttpClient sClient;
    private static Retrofit sRetrofit;
    private static Gson sGson;
    private static CatApi sApi;
    private RetrofitInitializer retrofitInitializer;
    private static final String RETROFIT_ERROR = "ERROR";
    public static final String API_URL = "https://api.thecatapi.com";

    public ApiService() {
        retrofitInitializer = new RetrofitInitializer();
    }

    private static OkHttpClient getClient() {
        if (sClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            if (!BuildConfig.BUILD_TYPE.contains("release")) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            sClient = builder.build();
        }
        return sClient;
    }





    public Single<List<Cat>> getCatList(int page) {
        return retrofitInitializer
                .catApi()
                .getAllCats(page, 20, "ASC", "small")
                .compose(RxFunctions.applySingleSchedulers())
                .doOnError( throwable -> Log.e(RETROFIT_ERROR, "Api getCatList error " + throwable));
    }



    private static Retrofit getRetrofit() {
        if (sGson == null) {
            sGson = new Gson();
        }
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(sGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static CatApi getApiService() {
        if (sApi == null) {
            sApi = getRetrofit().create(CatApi.class);
        }
        return sApi;
    }



}
