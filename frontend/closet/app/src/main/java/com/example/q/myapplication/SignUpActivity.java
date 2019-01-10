package com.example.q.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button item_detail, item_category_report;
    private ViewPager vp;
    private Find_EmailFragment emailFragment;
    private Find_PhoneFragment phoneFragment;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUserView;
    private EditText mEmailView;
    private EditText mPhoneView;
    private EditText mPasswordView;
    private View mProgressView1;
    private View mLoginFormView1;
    private View mProgressView2;
    private View mLoginFormView2;
    private String mEmail;
    private String mPassword;
    private UserLoginTask mAuthTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        mUserView = (AutoCompleteTextView) findViewById(R.id.username);
        populateAutoComplete();
        mEmailView = (EditText) findViewById(R.id.email);
        mPhoneView = (EditText) findViewById(R.id.phone);
        mPasswordView = (EditText) findViewById(R.id.password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    Intent intent=new Intent(SignUpActivity.this,HomeActivity.class);
//                    startActivity(intent);
//                    return true;
//                }
//                return false;
//            }
//        });
        mLoginFormView1 = findViewById(R.id.phone_login_form);
        mProgressView1 = findViewById(R.id.phone_login_progress);
        mLoginFormView2 = findViewById(R.id.email_login_form);
        mProgressView2 = findViewById(R.id.email_login_progress);
        Button signupbtn=findViewById(R.id.sign_up_button);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                writeLocalFile("test", "may");
                readLocalFile("user-info");
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

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
//        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mUserView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);
        mEmailView.setError(null);
        mPhoneView.setError(null);

        // Store values at the time of the login attempt.
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();
        String email=mEmailView.getText().toString();
        String phone=mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(phone) && !isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(user) && !isUserValid(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isEmailValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(user, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUserValid(String user) {
        //TODO: Replace this with your own logic
        return user.length() > 1;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.length() > 10;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.indexOf("@")==1;
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView1.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView1.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView1.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mLoginFormView2.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView2.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView2.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView1.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView1.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView1.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
            mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView2.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView1.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView1.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView2.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {


    private boolean attemptSignup() throws InterruptedException {
//        check if the value is valid
//        返回一个JSONObject，email或者phone如果没有就传空字符串
        JSONObject json_data = new JSONObject();
//        Map<String,String> map = new HashMap<String, String>();
//        这个只是一个实例，我是要给你们看怎么加东西
//        String username = vp.toString();
        String username = "may12";
        String password = "may123";
        String phone = "18916234567";
        String email = "http://120.76.62.132:80/img/6_8.png";
        String profile = "";
        String style = "casual";

//        String data = "";
        try{
            json_data.put("username", username);
            json_data.put("password", password);
            json_data.put("phone", phone);
            json_data.put("email", email);
            json_data.put("profile", profile);
            json_data.put("style", style);
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
//                .baseUrl("http://127.0.0.1:8000/")
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        final UsersPostRequest_Interface request = retrofit.create(UsersPostRequest_Interface.class);
        JSONObject object = null;
        users user = new users();
        MediaType textType = MediaType.parse("application/json");
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
            Response<sign> response = call.execute();
            sign result_sign = response.body();
            if(result_sign != null){
                String result = result_sign.getDataString();
                writeLocalFile("user-info", result);
                System.out.println("request call");
                return "ok";
            }else{
                if(response.errorBody() != null){
                    return response.errorBody().string();
                }
                return "error";
            }
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
            return "error";
        }
//        String username="xue";
//        String password="xue123456";
//        String base=username+":"+password;
//        String authorization="Basic "+ Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
//        RequestBody username = RequestBody.create(textType, object.getString("username"));
//        RequestBody match = RequestBody.create(textType, "1");
//        RequestBody name = RequestBody.create("text","11");
//        RequestBody age = RequestBody.create(MediaType.parse("text"), "24");
//        return "ok";
    }
      

        @Override
        public void run() {
            try{
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
            System.out.println("result: "+result);
            if(result == "ok"){
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if(result == "error" || result == ""){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "注册失败，请检测网络是否正常", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        result, Toast.LENGTH_SHORT);
                toast.show();
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

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mUser)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}