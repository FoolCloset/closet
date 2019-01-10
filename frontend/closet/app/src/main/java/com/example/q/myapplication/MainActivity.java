package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button signinbtn=findViewById(R.id.signinbutton);
        Button signupbtn=findViewById(R.id.signupbutton);



        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLocalFile("user-info")){
                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean readLocalFile(String file_name){
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
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
