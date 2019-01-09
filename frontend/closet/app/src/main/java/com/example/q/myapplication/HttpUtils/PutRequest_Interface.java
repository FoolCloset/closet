package com.example.q.myapplication.HttpUtils;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface PutRequest_Interface {
    @PUT("collections/")
    @Multipart
    Call<Collections>getPutCall(@Header("Authenticator") String authorization,
                                @Part("user") RequestBody name, @Part("match") RequestBody match);
}
