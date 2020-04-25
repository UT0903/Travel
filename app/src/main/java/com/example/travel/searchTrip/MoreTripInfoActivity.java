package com.example.travel.searchTrip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.travel.R;
import com.example.travel.TripInfo;

public class MoreTripInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_trip_info);
        TripInfo list = (TripInfo) getIntent().getSerializableExtra("data");
        list.getImage();
    }
}
