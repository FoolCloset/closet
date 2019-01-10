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
                finish();
            }
        });
        Button coat=findViewById(R.id.coat);
        coat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="coat";
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        Button jacket=findViewById(R.id.jacket);
        jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="jacket";
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        Button trousers=findViewById(R.id.trousers);
        trousers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="trousers";
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        Button shoes=findViewById(R.id.shoes);
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="shoes";
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        Button others=findViewById(R.id.others);
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="others";
                Intent intent=new Intent(ClosetActivity.this,ClothesSortActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
    }
}
