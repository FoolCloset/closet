package com.example.q.myapplication;

import android.content.Context;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.q.myapplication.HttpUtils.Const;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;

    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(HomeActivity.this,HomeActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_changhe:
                 Intent intent2 = new Intent(HomeActivity.this,OcassionsActivity.class);

                    startActivity(intent2);
                    return true;
                case R.id.navigation_photo:
                    Intent intent3 = new Intent(HomeActivity.this,PhotoActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        mTextMessage = (TextView) findViewById(R.id.message);
        TextView weather=(TextView)findViewById(R.id.textView5);
        weather.setText("\r\n今日天气："+Const.weathertext+"天\r\n\r\n"+"今日温度："+Const.temperature+"℃\r\n");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ImageButton like = (ImageButton)findViewById(R.id.likebutton);
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
        final ImageButton collect = (ImageButton)findViewById(R.id.collectbutton);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long

        // as you specify a parent activity in AndroidManifest.xml.
        int id_head = item.getItemId();

        if (id_head == R.id.action_search) {
                  Intent intent1 = new Intent(HomeActivity.this,OcassionsActivity.class);
                   startActivity(intent1);
        } else if (id_head == R.id.action_share) {
//                    Intent intent2 = new Intent(HomeActivity.this,PhotoActivity.class);                                        moonlight
//                    startActivity(intent2);                                                                                    moonlight
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_closet) {
            Intent intent = new Intent(HomeActivity.this,ClosetActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_collection) {
                  Intent intent = new Intent(HomeActivity.this,CollectionsActivity.class);
                   startActivity(intent);
        } else if (id == R.id.nav_information) {
            Intent intent = new Intent(HomeActivity.this,UserActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(HomeActivity.this,SignInActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
