package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ClothesDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);
        ImageButton back_sort_detail = (ImageButton) findViewById(R.id.back_sort_detail);
        back_sort_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClothesDetailActivity.this, SortDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
