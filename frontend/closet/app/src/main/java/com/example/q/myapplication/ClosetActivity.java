package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


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
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                startActivity(intent);
            }
        });
    }
}
