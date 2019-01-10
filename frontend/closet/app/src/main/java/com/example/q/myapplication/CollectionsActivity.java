package com.example.q.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.q.myapplication.HttpUtils.Collections;
import com.example.q.myapplication.HttpUtils.Const;
import com.example.q.myapplication.HttpUtils.GetRequest_Interface;
import com.example.q.myapplication.HttpUtils.Getimage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectionsActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
//    public HashMap<String, String> urls;
//    public HashMap<String,Bitmap>bitmapHashMap;
//    public Handler handler;
//    public final int SUCCESS1 = 1;
//    public final int SUCCESS2 = 2;
//    public final int FAILURE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
//        new Thread(){
//            public void run() {
//                request();
//            }
//        }.start();
        /*发送请求板块*/
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what) {
//                    case SUCCESS1:
//                        break;
//                    case SUCCESS2:
//                        HashMap<String,ImageView>imageViewHashMap=new HashMap<>();
//                        imageViewHashMap.put("0",(ImageView)findViewById(R.id.col1));
//                        imageViewHashMap.put("1",(ImageView)findViewById(R.id.col2));
//                        imageViewHashMap.put("2",(ImageView)findViewById(R.id.col3));
//                        imageViewHashMap.put("3",(ImageView)findViewById(R.id.col4));
//                        imageViewHashMap.put("4",(ImageView)findViewById(R.id.col5));
//                        imageViewHashMap.put("5",(ImageView)findViewById(R.id.col6));
//                       //遍历HashMap，将图片放在对应位置
//                        for(HashMap.Entry<String,Bitmap> entry:bitmapHashMap.entrySet()){
//                            imageViewHashMap.get(entry.getKey()).setImageBitmap(entry.getValue());
//                        }
//                        break;
//                    case FAILURE:
//                        Toast.makeText(CollectionsActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//        new Thread(){
//            public void run(){
//                request();
//            }
//        }.start();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton back_home1 = (ImageButton) findViewById(R.id.back_home1);
        back_home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectionsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton col1 = (ImageButton) findViewById(R.id.col1);
        col1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectionsActivity.this, SubCollectionActivity.class);
                startActivity(intent);
            }
        });


        final ImageButton cancel = (ImageButton) findViewById(R.id.colbutton1);
        cancel.setImageResource(R.drawable.startdark);
        cancel.setOnClickListener(new View.OnClickListener() {
            private int flag = 0;

            @Override

            public void onClick(View v) {
                if (flag == 0) {
                    cancel.setImageResource(R.drawable.starlight);
                    flag = 1;
                } else {
                    cancel.setImageResource(R.drawable.startdark);
                    flag = 0;
                }
            }
        });
    }
}
//    public void request() {
//
//        //步骤4:创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://100.67.109.140:8000/") // 设置 网络请求 Url
////                .baseUrl("http://120.76.62.132:80/")
//                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
//                .build();
//
//        // 步骤5:创建 网络请求接口 的实例
//        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
////        MediaType textType = MediaType.parse("text/plain");
////        RequestBody u = RequestBody.create(textType, "xue");
//        String userid=Const.userid;
//
//        //对 发送请求 进行封装
//        Call<Collections> call = request.getCollectionCall(userid);
//
//        //步骤6:发送网络请求(异步)
//        call.enqueue(new Callback<Collections>() {
//            //请求成功时回调
//            @Override
//            public void onResponse(Call<Collections> call, Response<Collections> response) {
//                /*将图片的Url信息读出来*/
//                for(int i=0;i<(response.body()).getResults().size();i++){
//                    String url=null;
//                    /*获取返回信息中的url信息*/
//                    if(response.body().getResults().get(i)!=null){
//                        url=response.body().getResults().get(i).getSnapshot();
//                    }
//                    urls.put(Integer.toString(i),url);
//
//                }
//                new Thread(){
//                    public void run(){
//                        requestImage();
//                    }
//                }.start();
//                Message msg=new Message();
//                msg.obj=null;
//                msg.what=SUCCESS1;
//                handler.sendMessage(msg);
//            }
//
//            //请求失败时回调
//            @Override
//            public void onFailure(Call<Collections> call, Throwable throwable) {
//                Message msg = new Message();
//
//                msg.obj = null;
//                msg.what = FAILURE;
//                handler.sendMessage(msg);
//                System.out.println("连接失败");
//            }
//        });
//    }
//    public void requestImage(){
//            try{
//                for(HashMap.Entry<String,String> entry:urls.entrySet()){
//                    //将序号和BitMap对应
//                    bitmapHashMap.put(entry.getKey(),Getimage.getImage(entry.getValue()));
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            Message msg=new Message();
//            msg.obj=bitmapHashMap;
//            msg.what=SUCCESS2;
//            handler.sendMessage(msg);
//    }

