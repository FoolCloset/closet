package com.example.q.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClothesSortActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> List = new ArrayList<>();
    private ListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_sort);
        listView = (ListView) findViewById(R.id.listView1);
        Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        if(type.equals("coat")){
            List.add("西装外套");
            List.add("羽绒服");
            List.add("牛仔外套");
            List.add("运动外套");
            List.add("其他");
        }else if(type.equals("jacket")){
            List.add("衬衫");
            List.add("卫衣");
            List.add("T恤");
            List.add("其他");
        }else if(type.equals("trousers")){
            List.add("牛仔裤");
            List.add("运动裤");
            List.add("西装裤");
            List.add("其他");
        }else if(type.equals("shoes")){
            List.add("皮鞋");
            List.add("运动鞋");
            List.add("休闲鞋");
            List.add("其他");
        }else if(type.equals("others")){
            List.add("领带");
            List.add("皮带");
            List.add("袖箍");
            List.add("其他");
        }

        adapter = new ListviewAdapter(ClothesSortActivity.this, List);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ClothesSortActivity.this,SortDetailActivity.class);
                String sort=List.get(i);
                intent.putExtra("sort",sort);
                startActivity(intent);
            }
        });

        adapter.setOnItemDeleteClickListener(new ListviewAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                Intent intent=new Intent(ClothesSortActivity.this,SortDetailActivity.class);
                String sort=List.get(i);
                intent.putExtra("sort",sort);
                startActivity(intent);
            }
        });
        ImageButton back_closet=findViewById(R.id.back_closet);
        back_closet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClothesSortActivity.this,ClosetActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
