package com.example.q.myapplication.HttpUtils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostRequest_Interface {
    @POST("collections/")
    @Multipart
    Call<Collections> getCollectionsCall(@Header("Authorization") String authorization,
                                  @Part("user") RequestBody name, @Part("match") RequestBody age);

    @POST("upload/")
    @Multipart
    Call<imageResponse>getImageResponseCall(@Part("username") RequestBody name, @Part("password")RequestBody password,
                                            @Part MultipartBody.Part file);
}
