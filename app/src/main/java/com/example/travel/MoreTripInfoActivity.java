package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MoreTripInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_trip_info);
        TripInfo list = (TripInfo) getIntent().getSerializableExtra("data");
        list.getImage();
    }
}
