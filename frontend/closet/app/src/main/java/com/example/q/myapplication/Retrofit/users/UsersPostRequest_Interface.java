package com.example.q.myapplication.Retrofit.users;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface UsersPostRequest_Interface {
    @POST("sign-up/")
    @Multipart
    Call<users> getCall(@Header("Authorization") String authorization,
                          @Part("user") RequestBody name, @Part("users") RequestBody users);

    @POST("sign-up/")
    Call<sign> getCall(@Body users para);

    @POST("sign-up/")
    @Multipart
    Call<sign> getMultiCall(@Part users para);
}
