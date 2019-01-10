package com.example.q.myapplication.Retrofit.clothes;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ClothesPutRequest_Interface {
    @PUT("clothes/")
    @Multipart
    Call<clothes> getCall(@Header("Authenticator") String authorization,
                          @Part("user") RequestBody name, @Part("clothes") RequestBody clothes);
}