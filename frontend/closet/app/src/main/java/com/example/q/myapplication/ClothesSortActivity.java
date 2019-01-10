package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ClothesSortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_sort);
        ImageButton back_closet=(ImageButton)findViewById(R.id.back_closet);
        back_closet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClothesSortActivity.this,ClosetActivity.class);
                startActivity(intent);
            }
        });

        Button coat_sort1=(Button)findViewById(R.id.coat_sort1);
        coat_sort1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClothesSortActivity.this,SortDetailActivity.class);
                startActivity(intent);
            }
        });

        ImageButton sort_enter1=(ImageButton)findViewById(R.id.sort_enter1);
        sort_enter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClothesSortActivity.this,SortDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
