package com.example.q.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.q.myapplication.HttpUtils.Const;
import com.example.q.myapplication.HttpUtils.GetRequest_Interface;
import com.example.q.myapplication.HttpUtils.PostRequest_Interface;
import com.example.q.myapplication.HttpUtils.Weather;
import com.example.q.myapplication.HttpUtils.imageResponse;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public Handler handler;
    final int SUCCESS=1;
    final int FAILURE=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signinbtn=findViewById(R.id.signinbutton);
        Button signupbtn=findViewById(R.id.signupbutton);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SUCCESS:
//                        int T=msg.obj.
//                        Const.setWeather();
                        Const.temperature=((Weather.ResultsBean) msg.obj).getNow().getTemperature();
                        Const.weathertext=((Weather.ResultsBean)msg.obj).getNow().getText();
                        break;
                    case FAILURE:
                        break;
                }
            }
        };
        //获取天气
        new Thread(){
            public  void run(){
                request();
            }
        }.start();
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录的时候为用户获取天气情况//

                Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void request(){
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.weather_url) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        //对 发送请求 进行封装
        Call<Weather> call = request.getWeatherCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Weather>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                response.body();
                Message msg=new Message();
                msg.obj=response.body().getResults().get(0);
                msg.what=SUCCESS;
                handler.sendMessage(msg);

                // 步骤7：处理返回的数据结果
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Weather> call, Throwable throwable) {
                Message msg=new Message();
                msg.obj=null;
                msg.what=FAILURE;
                handler.sendMessage(msg);
                System.out.println("请求失败");
            }
        });
    }
}
