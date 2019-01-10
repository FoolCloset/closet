package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.q.myapplication.Retrofit.clothes.ClothesGetRequest_Interface;
import com.example.q.myapplication.Retrofit.clothes.clothes;
import com.example.q.myapplication.Retrofit.clothesList.ClothesListGetRequest_Interface;
import com.example.q.myapplication.Retrofit.clothesList.clothesList;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class ClosetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        ImageButton returnhome2=findViewById(R.id.returnhome2);
        returnhome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClosetActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        Button coat=findViewById(R.id.coat);
        coat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "coat";
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        Button jacket=findViewById(R.id.jacket);
        jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                startActivity(intent);
            }
        });

        Button trousers=findViewById(R.id.trousers);
        trousers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                startActivity(intent);
            }
        });

        Button shoes=findViewById(R.id.shoes);
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                startActivity(intent);
            }
        });

        Button others=findViewById(R.id.others);
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                startActivity(intent);
            }
        });

        request();
    }

    public void request(){
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.76.62.132:80/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        final ClothesListGetRequest_Interface request = retrofit.create(ClothesListGetRequest_Interface.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        HashMap<String, String> para = new HashMap<>();
        para.put("type", "shoes");
        Call<clothesList> call = request.getCall(para);

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<clothesList>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<clothesList> call, Response<clothesList> response) {
                // 步骤7：处理返回的数据结果：输出翻译的内容
//                System.out.println(response.body().getTranslateResult().get(0).get(0).getTgt());
//                System.out.println(response.body().);
                System.out.println("clothes request success");
                System.out.println(response.body().getCount());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<clothesList> call, Throwable throwable) {
                System.out.println("请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
