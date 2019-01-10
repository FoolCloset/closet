package com.example.q.myapplication.HttpUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
* 可以通用的HTTPUtil 工具
* 2018/12/31
* */
public class Getimage {
    /*通过url获取图片*/
    public static  Bitmap getImage(String path)throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时时间为5秒
        conn.setConnectTimeout(5000);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("请求失败");
        }
        InputStream instream = conn.getInputStream();
        byte[] data = read(instream);
        Bitmap bitmap= BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }
    public static  byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len=0;
        while((len=inStream.read(buffer))!=-1)
        {
            outStream.write(buffer,0,len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
