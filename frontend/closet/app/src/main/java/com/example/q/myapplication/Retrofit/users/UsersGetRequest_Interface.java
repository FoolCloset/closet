package com.example.q.myapplication.Retrofit.users;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UsersGetRequest_Interface {
//    @GET("users/")
//    Call<users>getCall();

    @GET("users/")
    Call<users>getCall(@Query("user") String user_id);
}
