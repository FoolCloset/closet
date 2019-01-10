package com.example.q.myapplication.Retrofit.clothes;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ClothesPostRequest_Interface {
    @POST("clothes/")
    @Multipart
    Call<clothes> getCall(@Header("Authorization") String authorization,
                          @Part("user") RequestBody name, @Part("clothes") RequestBody clothes);
}
