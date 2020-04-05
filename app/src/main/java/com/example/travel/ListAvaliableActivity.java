package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ListAvaliableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_avaliable);
        Intent it = getIntent();
        String dest = it.getStringExtra("dest");
        String date = it.getStringExtra("date");
        Toast.makeText(this, "dest = " + dest + "date =" + date + "=", Toast.LENGTH_LONG).show();
        Api.findAvaliableTrip(dest, date);
    }
}
