package com.example.q.myapplication.Retrofit.clothes;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public interface ClothesDeleteRequest_Interface {
    @DELETE("clothes/")
    @Multipart
    Call<clothes> getdeletecall(@Header("Authenticator")String authenticator,
                                @Part("id") RequestBody ID);
}
