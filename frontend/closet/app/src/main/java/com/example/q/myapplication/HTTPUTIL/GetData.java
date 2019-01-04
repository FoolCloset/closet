package com.example.q.myapplication.HTTPUTIL;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class GetData {
    //定义一个获取图片数据的方法
    public static  byte[] getImage(String path,String Json)throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时时间为5秒
        conn.setConnectTimeout(5000);
        conn.setDoInput(true);
        conn.setDoOutput(true);

       /* //设置文件类型
        conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");

        // 往服务器里面发送数据
        if (Json != null && !TextUtils.isEmpty(Json)) {
            byte[] writebytes = Json.getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
            OutputStream outwritestream = conn.getOutputStream();
            outwritestream.write(Json.getBytes());
            outwritestream.flush();
            outwritestream.close();
        }*/
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("请求失败");
        }
        InputStream instream = conn.getInputStream();
        byte[] data = StreamTool.read(instream);
        return data;
    }

    //获取网页的html源代码
    public static String getHtml(String path) throws Exception{
        URL url=new URL(path);
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept","application/json");
        if(conn.getResponseCode()!=200){
            throw new RuntimeException("请求html失败");
        }
        InputStream instream=conn.getInputStream();
        byte[] data=StreamTool.read(instream);
        String html=new String(data,"UTF-8");
        conn.disconnect();
        return html;
    }
}
