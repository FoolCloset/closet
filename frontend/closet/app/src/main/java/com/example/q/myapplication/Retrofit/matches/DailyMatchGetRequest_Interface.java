package com.example.q.myapplication.Retrofit.matches;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DailyMatchGetRequest_Interface {
    @GET("matches/")
    Call<matches> getCall();

    @GET("matches/")
    Call<matches>getCall(@Query("user") String user_id);
}

