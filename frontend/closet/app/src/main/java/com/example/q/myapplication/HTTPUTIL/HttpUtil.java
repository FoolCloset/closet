package com.example.q.myapplication.HTTPUTIL;
import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/*
* 可以通用的HTTPUtil 工具
* 2018/12/31
* */
public class HttpUtil {
    /*Get请求*/
    public static String executeGetMethod(String path/*, String authorization*/) {
        String response = "";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            /*connection.setRequestProperty("Authorization", "Basic " + authorization);*/
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            // 获得返回值
            InputStream in = connection.getInputStream();
            response = getResponse(in);
//            Log.i("response", response);
            if (connection.getResponseCode() != 200)
            {
                Log.i("code",String.valueOf(connection.getResponseCode()));
                throw new RuntimeException("请求失败");
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /*Put请求withJson文件*/
    public static String executePostMethod(String path, JSONObject param) throws SocketTimeoutException {
        String paramStr = param.toString();
        String response = "";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(paramStr.length()));
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            // 写入请求的字符串
            out.writeBytes(paramStr);
            out.flush();
            out.close();

            // 获得返回值
            InputStream in = connection.getInputStream();
            response = getResponse(in);
            if (connection.getResponseCode() == 400)
            {
                Log.i("code",String.valueOf(connection.getResponseCode()));
                throw new RuntimeException("请求失败");
            }
            connection.disconnect();
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /*通过url获取图片*/
    public static  byte[] getImage(String path)throws Exception {
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
        byte[] data = StreamTool.read(instream);
        return data;
    }

    private static String getResponse(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
