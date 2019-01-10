package com.example.q.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.q.myapplication.Retrofit.matches.DailyMatchGetRequest_Interface;
import com.example.q.myapplication.Retrofit.matches.matches;
import com.example.q.myapplication.Retrofit.users.UsersPostRequest_Interface;
import com.example.q.myapplication.Retrofit.users.sign;
import com.example.q.myapplication.Retrofit.users.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import library.HttpUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button item_detail, item_category_report;
    private ViewPager vp;
    private Find_EmailFragment emailFragment;
    private Find_PhoneFragment phoneFragment;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private String data = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        Button signupbtn=findViewById(R.id.sign_up_button);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeLocalFile("test", "may");
                readLocalFile("test");
                try {
                    attemptSignup();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                if(attemptSignup()) {
//                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }

            }
        });


        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(2);//ViewPager的缓存为2帧
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        item_detail.setTextColor(Color.parseColor("#1ba0e1"));

        //ViewPager的监听事件
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }

    /**
     * 初始化布局View
     */
    private void initViews() {
        item_detail = findViewById(R.id.item_detail);
        item_category_report = findViewById(R.id.item_category_report);

        item_detail.setOnClickListener(this);
        item_category_report.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        emailFragment = new Find_EmailFragment();
        phoneFragment = new Find_PhoneFragment();
        //给FragmentList添加数据
        mFragmentList.add(emailFragment);
        mFragmentList.add(phoneFragment);
    }

    /**
     * 点击头部Text 动态修改ViewPager的内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_detail:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_category_report:
                vp.setCurrentItem(1, true);
                break;
        }
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    /**
     * 由ViewPager的滑动修改头部导航Text的颜色
     * @param position
     */
    private void changeTextColor(int position) {
        if (position == 0) {
            item_detail.setTextColor(Color.parseColor("#8aabd5"));
            item_category_report.setTextColor(Color.parseColor("#000000"));
        } else if (position == 1) {
            item_category_report.setTextColor(Color.parseColor("#8aabd5"));
            item_detail.setTextColor(Color.parseColor("#000000"));
        }
    }

    private boolean attemptSignup() throws InterruptedException {
//        check if the value is valid
//        返回一个JSONObject，email或者phone如果没有就传空字符串
        JSONObject json_data = new JSONObject();
//        Map<String,String> map = new HashMap<String, String>();
//        这个只是一个实例，我是要给你们看怎么加东西
        String username = vp.toString();
//        String data = "";
        try{
            json_data.put("username", "may33");
            json_data.put("password", "may123");
            json_data.put("phone", "18967223241");
            json_data.put("email", "");
            json_data.put("profile", "");
            json_data.put("style", "casual");
            data = json_data.toString();
//            String data = json
        }catch (JSONException e){
            e.printStackTrace();
        }
// 后面data转成String类型

//        if(sendSignUpRequest(data)){
//            System.out.println("ok");
//            return true;
//        }else{
//            return false;
//        }


        Thread thread = new Thread(runnable);
        thread.start();
//            thread.join();
        return true;
    }

    public String request(){
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.76.62.132:80/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        final UsersPostRequest_Interface request = retrofit.create(UsersPostRequest_Interface.class);
        JSONObject object = null;
        users user = new users();
        MediaType textType = MediaType.parse("text/plain");
        try {
            object = new JSONObject(data);
            user.setId(1);
            user.setEmail(object.getString("email"));
            user.setPhone(object.getString("phone"));
            user.setProfile(object.getString("profile"));
            user.setStyle(object.getString("style"));
            user.setUsername(object.getString("username"));
            user.setPassword(object.getString("password"));
//            RequestBody username = RequestBody.create(textType, object.getString("username"));
//            RequestBody password = RequestBody.create(textType, object.getString("password"));
//            RequestBody profile = RequestBody.create(textType, object.getString("profile"));
//            RequestBody style = RequestBody.create(textType, object.getString("style"));
//            RequestBody email = RequestBody.create(textType, object.getString("email"));
//            RequestBody phone = RequestBody.create(textType, object.getString("phone"));

            //对 发送请求 进行封装
            Call<sign> call = request.getCall(user);
            sign result_sign = call.execute().body();
            String result = result_sign.getDataString();
            System.out.println("request call");
            return result;
//            new Thread(){call.execute().body();}.start();


////            步骤6:发送网络请求(异步)
//            call.enqueue(new Callback<sign>() {
//                //请求成功时回调
//                @Override
//                public void onResponse(Call<sign> call, Response<sign> response) {
//                    String a;
////                    response.body();
//                    // 步骤7：处理返回的数据结果
//                    if(response.body() != null){
//                        response.body().show();
//                    }else{
//                        try {
//                            String err_msg = response.errorBody().string();
//                            Log.i("code", err_msg);
//                            System.out.println(err_msg);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    System.out.println("hello world");
//                    //response.body就是我声明的类
//                }
//
//                //请求失败时回调
//                @Override
//                public void onFailure(Call<sign> call, Throwable throwable) {
//                    System.out.println("fail to connect");
//                }
//            });

        }catch (JSONException e){
            e.printStackTrace();
            return "error";
        }catch (IOException e){
            e.printStackTrace();
        }
//        String username="xue";
//        String password="xue123456";
//        String base=username+":"+password;
//        String authorization="Basic "+ Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
//        RequestBody username = RequestBody.create(textType, object.getString("username"));
//        RequestBody match = RequestBody.create(textType, "1");
//        RequestBody name = RequestBody.create("text","11");
//        RequestBody age = RequestBody.create(MediaType.parse("text"), "24");


        return "ok";
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try{
//                String data = "";
                String result = request();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String result = data.getString("result");
            if(result != "ok"){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "注册失败，请检测网络是否正常", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
//            tv_request_result.setText(result);
        }
    };

    private boolean sendSignUpRequest(final String data){
        PostRunThread sign_up_thread = new PostRunThread("sign-up", data);
        sign_up_thread.start();
        try{
            sign_up_thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
//        sign_up_thread.run();
        if(sign_up_thread.getRunLog().toLowerCase() != "ok"){
            return false;
        }else{
            System.out.println(sign_up_thread.getRunLog());
        }
        return true;
    }

    class PostRunThread extends Thread{
        private String name;
        private String run_log = "";
        private String data;

        public PostRunThread(String name, String data){
            this.name = name;
            this.data = data;
        }

        public void run(){
            try{
//                String url = "http://120.76.62.132:80/" + this.name + "/";
                String url = "http://120.76.62.132/sign-up/";
                JSONObject para = new JSONObject(this.data);
                String detail = HttpUtil.executePostMethod(url, para);
                JSONObject object = null;
                object = new JSONObject(detail);
                String msg = object.getString("msg");
                if(msg == null){
                    msg = "ok";
                }
                this.run_log = msg;
                return;
//                    JSONArray result =  object.optJSONArray("results");
            }catch (JSONException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        public String getRunLog(){
            return this.run_log;

        }

    }

    private void writeLocalFile(String file_name, String data){
        try{
            String DIR_NAME = "";
//            String FILE_NAME = "test";
            String dir_path = getCacheDir().getAbsolutePath();
//            String dir_path = Environment.getDataDirectory().getAbsoluteFile().getAbsolutePath()
//                    + File.separator + DIR_NAME;
            File file = new File(dir_path);
            if(!file.exists())
                file.mkdir();
            file = new File(dir_path + File.separator + file_name);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter file_writer = new OutputStreamWriter(fos, "UTF-8");
            file_writer.write("this is test");
            file_writer.write(dir_path);
            file_writer.flush();
            fos.flush();
            file_writer.close();
            fos.close();
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readLocalFile(String file_name){
        try{
            String DIR_NAME = "";
//            String FILE_NAME = "test";
            String dir_path = getCacheDir().getAbsolutePath();
//            String dir_path = Environment.getDataDirectory().getAbsoluteFile().getAbsolutePath()
//                    + File.separator + DIR_NAME;
            File file = new File(dir_path);
            file = new File(dir_path + File.separator + file_name);
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(file, true);
            InputStreamReader file_reader = new InputStreamReader(fis, "UTF-8");
            char[] input = new char[fis.available()];
            file_reader.read(input);
            file_reader.close();
            fis.close();
            String data = new String(input);
            System.out.println(data);
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}