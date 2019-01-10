package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class ClothesDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);
        ImageButton back_sort_detail = (ImageButton) findViewById(R.id.back_sort_detail);

        writeLocalFile("clothesDetail", "may");

        readLocalFile("clothesDetail");
        back_sort_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClothesDetailActivity.this, SortDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
