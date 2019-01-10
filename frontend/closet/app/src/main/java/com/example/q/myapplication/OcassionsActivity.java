package com.example.q.myapplication;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.content.Intent;

        import android.widget.SearchView;

        import android.widget.SearchView.OnQueryTextListener;
        import android.widget.ImageButton;




        public class OcassionsActivity extends AppCompatActivity{
        private SearchView mSearchView;
   @Override
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocassions);
           ImageButton back_home4=(ImageButton)findViewById(R.id.back_home4);
          back_home4.setOnClickListener(new View.OnClickListener(){
                   @Override
                   public void onClick(View v){
                           Intent intent=new Intent(OcassionsActivity.this,HomeActivity.class);
                           startActivity(intent);
                   }
           });
        Button party_button=(Button)findViewById(R.id.party_button);
        party_button.setOnClickListener(new View.OnClickListener(){
 @Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,MatchActivity.class);
        startActivity(intent);
        }
        });
        Button wedding_button=(Button)findViewById(R.id.wedding_button);
        wedding_button.setOnClickListener(new View.OnClickListener(){
   @Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,MatchActivity.class);
        startActivity(intent);
        }
        });
        Button date_button=(Button)findViewById(R.id.date_button);
        date_button.setOnClickListener(new View.OnClickListener(){
 @Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,MatchActivity.class);
        startActivity(intent);
        }
        });
        Button work_button=(Button)findViewById(R.id.work_button);
        work_button.setOnClickListener(new View.OnClickListener(){
            @Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,MatchActivity.class);
        startActivity(intent);
        }
        });
        Button school_button=(Button)findViewById(R.id.school_button);
        school_button.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,MatchActivity.class);
        startActivity(intent);
        }
        });
        Button home_button=(Button)findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,MatchActivity.class);
        startActivity(intent);
        }
        });
        Button rain_button=(Button)findViewById(R.id.rain_button);
        rain_button.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,CollectionsActivity.class);
        startActivity(intent);
        }
        });
        Button banquet_button=(Button)findViewById(R.id.banquet_button);
        banquet_button.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,MatchActivity.class);
        startActivity(intent);
        }
        });
        Button outdoor_button=(Button)findViewById(R.id.outdoor_button);
        outdoor_button.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View v){
        Intent intent=new Intent(OcassionsActivity.this,MatchActivity.class);
        startActivity(intent);
        }
        });


        mSearchView=(SearchView)findViewById(R.id.searchView);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();
        mSearchView.setOnQueryTextListener(new OnQueryTextListener(){
@Override
public boolean onQueryTextChange(String queryText){
        System.out.println("onQueryTextChange:"+queryText);
        return true;
}
  @Override
public boolean onQueryTextSubmit(String queryText){
        System.out.println("onQueryTextSubmit:"+queryText);
        return true;
}
        });


        }
        }
