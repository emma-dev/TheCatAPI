package com.example.catapi.api;

import com.example.catapi.BuildConfig;
import com.example.catapi.modal.Cat;
import com.example.catapi.modal.CatsModal;
import com.example.catapi.modal.UploadCat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CatApi {


     String API_KEY = "9b7e282d-2a67-4c7b-a9fd-3f3e4056e949";

   @Headers({"Content-Type: multipart/form-data", "x-api-key: " + API_KEY})
    @GET("v1/images/search/")
    Single<List<Cat>> getAllCats(@Query("page") int page,
                                 @Query("limit") int limit,
                                 @Query("order") String order,
                                 @Query("size") String size);


    @Headers({"Content-Type: multipart/form-data", "x-api-key: " + API_KEY})
    @Multipart
    @POST("/images/upload")
    Call<UploadCat> getUploadCat(@Part MultipartBody.Part part);



}
