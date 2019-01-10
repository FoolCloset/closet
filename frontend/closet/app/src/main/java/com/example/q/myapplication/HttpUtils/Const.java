package com.example.q.myapplication.HttpUtils;

import android.util.Base64;
/*当前登陆用户的密码和用户名*/
public  class Const {
    public static String username;
    public static String password;
    public static String userid;
    public static final  String url="http://192.168.31.234:8000/";
    public static final  String weather_url="https://api.seniverse.com/";
    public static String temperature;
    public static String weathertext;
    public static String get_token(){
        String base=username+":"+password;
        String authorization="Basic "+Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        return authorization;
    }
//    public static  String get_url(){
//        return url;
//    }
//
//    public static void setWeather(String T,String W){
//        weathertext=W;
//        temperature=T;
//    }
}
