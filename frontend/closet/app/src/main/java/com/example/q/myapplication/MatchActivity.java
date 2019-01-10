package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.MotionEvent;

public class MatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        ImageButton back_search =(ImageButton) findViewById(R.id.back_search);
        back_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton like = (ImageButton)findViewById(R.id.like_button2);
        like.setImageResource(R.drawable.noheart);
        like.setOnClickListener(new View.OnClickListener() {
            private int flag=0;
            @Override
            public void onClick(View v) {
                if(flag==0) {
                    like.setImageResource(R.drawable.heart);
                    flag=1;
                }
                else {
                    like.setImageResource(R.drawable.noheart);
                    flag=0;
                }

            }
        });
        final ImageButton collect = (ImageButton)findViewById(R.id.collect_button3);

        collect.setImageResource(R.drawable.startdark);
        collect.setOnClickListener(new View.OnClickListener() {
            private int flag=0;
            @Override

            public void onClick(View v) {
               if(flag == 0)
               {
                   collect.setImageResource(R.drawable.starlight);
                   flag=1;
               }
               else
               {
                   collect.setImageResource(R.drawable.startdark);
                   flag=0;
               }
            }
        });

    }
}
