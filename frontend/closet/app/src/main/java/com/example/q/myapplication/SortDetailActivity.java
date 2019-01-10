package com.example.q.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class SortDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ImageButton back_sort=(ImageButton)findViewById(R.id.back_sort);
        back_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SortDetailActivity.this,ClothesSortActivity.class);
                startActivity(intent);
            }
        });
        ImageButton cloth1=(ImageButton)findViewById(R.id.cloth1);
        cloth1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SortDetailActivity.this,ClothesDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
