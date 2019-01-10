package com.example.q.myapplication.HttpUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRequest_Interface {
    //获得天气
    @GET("v3/weather/now.json?key=rp1jtnb6kembxgcj&location=shanghai&language=zh-Hans&unit=c")
    Call<Weather>getWeatherCall();
    //收藏的get
    @GET("collections/")
    Call<Collections>getCollectionCall();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法

    //衣服的get
    @GET("clothes/")
    Call<Clothes>getClothesCall(@Query("user") String userid);

    //搭配的get
    @GET("match/")
    Call<Match>getMatchCall(@Query("user") String userid);
}
