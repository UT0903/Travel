package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.order.OrderActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
    }
    public void searchAvailable(View v){
        //Toast.makeText(HomeActivity.this, "here", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(HomeActivity.this, SearchAvailableActivity.class);
        startActivity(it);
    }
    public void orderTrip(View v){
        Intent it = new Intent(HomeActivity.this, OrderTripActivity.class);
        startActivity(it);
    }
    public void queryTrip(View v){
        Intent it = new Intent(HomeActivity.this, OrderActivity.class);
        startActivity(it);
    }

}
