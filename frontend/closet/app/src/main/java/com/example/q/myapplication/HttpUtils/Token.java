package com.example.q.myapplication.HttpUtils;

import android.util.Base64;
/*当前登陆用户的密码和用户名*/
public  class Token {
    public static String username;
    public  static String password;
    public static String get_token(){
        String base=username+":"+password;
        String authorization="Basic "+Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        return authorization;
    }
}
