package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }
    public void searchAvailable(View v){
        //Toast.makeText(MainActivity.this, "here", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(MainActivity.this, SearchAvailableActivity.class);
        startActivity(it);
    }
    public void orderTrip(View v){
        Intent it = new Intent(MainActivity.this, OrderTripActivity.class);
        startActivity(it);
    }
    public void modifyTrip(View v){
        Intent it = new Intent(MainActivity.this, ModifyTripActivity.class);
        startActivity(it);
    }
    public void queryTrip(View v){
        Intent it = new Intent(MainActivity.this, QueryTripActivity.class);
        startActivity(it);
    }

}
