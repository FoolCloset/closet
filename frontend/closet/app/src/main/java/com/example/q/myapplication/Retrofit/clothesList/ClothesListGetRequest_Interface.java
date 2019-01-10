package com.example.q.myapplication.Retrofit.clothesList;

import com.example.q.myapplication.Retrofit.clothes.clothes;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ClothesListGetRequest_Interface {
    @GET("clothes/")
    Call<clothesList> getCall();

    @GET("clothes/")
    Call<clothesList> getCall(@Query("user") int user_id, @Query("type") String type,
                              @Query("color") String color, @Query("season") String season,
                              @Query("pattern") String pattern, @Query("subtype") String subtype);

    @GET("clothes/")
    Call<clothesList> getCall(@QueryMap Map<String, String> para);
}
