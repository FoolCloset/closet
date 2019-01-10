package com.example.q.myapplication.Retrofit.users;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public interface UsersDeleteRequest_Interface {
    @DELETE("users/")
    @Multipart
    Call<users> getdeletecall(@Header("Authenticator")String authenticator,
                                @Part("id") RequestBody ID);
}
