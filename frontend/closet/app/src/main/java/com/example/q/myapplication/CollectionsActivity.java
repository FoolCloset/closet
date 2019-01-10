package com.example.q.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.graphics.Matrix;

public class CollectionsActivity extends AppCompatActivity {
    /** Called when the activity is first created. */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton back_home1 = (ImageButton) findViewById(R.id.back_home1);
        back_home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectionsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton col1 = (ImageButton) findViewById(R.id.col1);
        col1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectionsActivity.this,  SubCollectionActivity.class);
                startActivity(intent);
            }
        });


        final ImageButton cancel = (ImageButton)findViewById(R.id.colbutton1);

        cancel.setImageResource(R.drawable.startdark);
        cancel.setOnClickListener(new View.OnClickListener() {
            private int flag=0;
            @Override

            public void onClick(View v) {
                if(flag == 0)
                {
                    cancel.setImageResource(R.drawable.starlight);
                    flag=1;
                }
                else
                {
                    cancel.setImageResource(R.drawable.startdark);
                    flag=0;
                }
            }
        });






    }


}
