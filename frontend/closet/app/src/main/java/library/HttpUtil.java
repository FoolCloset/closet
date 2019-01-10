package library;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.HashMap;
import java.util.Map;

/*
* 可以通用的HTTPUtil 工具
* 2018/12/31
* */
public class HttpUtil {
    private String BASE_URL = "http:120.76.62.132:80/";
    /*Get请求*/
    public static String executeGetMethod(String path/*, String authorization*/) {
        String response = "";
        Map result = new HashMap();
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
            result.put("code", connection.getResponseCode());
            result.put("json", response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /*Post请求withJson文件*/
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
            if(connection.getResponseCode() != 500){
                InputStream in = connection.getInputStream();
                response = getResponse(in);
            }
//            InputStream in = connection.getInputStream();
//            response = getResponse(in);
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

    public boolean post(final String url_suffix, final String data){
        new Thread(){
            public void run(){
                try {
                    String detail;
//                            detail = GetData.getHtml(URL);
//                            System.out.print(detail);
//                           detail=HttpUtil.executeGetMethod(URL);
                    //转成json文件
                    JSONObject object=null;
                    try{
                        JSONObject json_para=new JSONObject(data);
                        detail=HttpUtil.executePostMethod(BASE_URL+url_suffix+"/",json_para);
                        object=new JSONObject(detail);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    /*解析json对象*/
                    JSONArray result=object.optJSONArray("results");
                    //获取url
//                            String url=new JSONObject(detail).optJSONArray("results").get(1).get("url");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return true;
    }
}
