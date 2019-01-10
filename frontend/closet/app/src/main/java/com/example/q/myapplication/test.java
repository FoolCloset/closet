package com.example.q.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test extends AppCompatActivity {

    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent=getIntent();
        String sort=intent.getStringExtra("sort");
        gridView = (GridView) findViewById(R.id.sortgrid);
        //初始化数据
        initData();
        String[] from={"img"};
        int[] to={R.id.cloth1};
        adapter=new SimpleAdapter(this, dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent=new Intent(SortDetailActivity.this,ClothesDetailActivity.class);
                startActivity(intent);
            }
        });

        ImageButton back_sort=(ImageButton)findViewById(R.id.back_sort);
        back_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SortDetailActivity.this,ClothesSortActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    void initData() {
        //图标
        int pic[] = { R.drawable.blue_suit, R.drawable.pic1, R.drawable.pic2,R.drawable.blue_suit, R.drawable.pic1, R.drawable.pic2,R.drawable.blue_suit, R.drawable.pic1, R.drawable.pic2,};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <pic.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("img", pic[i]);
            dataList.add(map);
        }
    }
}
