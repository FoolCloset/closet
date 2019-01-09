package com.example.q.myapplication.HttpUtils;

import java.util.Collections;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Query;

public interface DeleteRequest_Interface {
    @DELETE("collections/")
    @Multipart
    Call<Collections> getdeletecall(@Header("Authenticator") String authenticator,
                                    @Query("id") RequestBody ID);
}
