package com.example.travel.searchTrip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.travel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MoreTripInfoActivity extends AppCompatActivity {
    private TextView number_of_people, title, start_date, end_date;
    private Button increaseBtn, decreaseBtn;
    private TripInfo data;
    private int num = 0;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_trip_info);
        data = (TripInfo) getIntent().getSerializableExtra("data");

        title = (TextView) findViewById(R.id.trip_title);
        title.setText(data.getTitle());

        start_date = (TextView) findViewById(R.id.trip_start_date);
        start_date.setText(data.getStart_date());

        end_date = (TextView) findViewById(R.id.trip_end_date);
        end_date.setText(data.getEnd_date());

        number_of_people = (TextView) findViewById(R.id.trip_num_of_people);
        increaseBtn = (Button) findViewById(R.id.trip_increase);
        decreaseBtn = (Button) findViewById(R.id.trip_decrease);

        decreaseBtn.setEnabled(false);
        number_of_people.setText(String.valueOf(num));

        //Log.e("TAG", data.getTitle() + data.getStart_date() + data.getEnd_date() + String.valueOf(num));
        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case (R.id.trip_decrease):
                        num--;
                        break;
                    case (R.id.trip_increase):
                        num++;
                        break;
                }
                if (num >= Integer.valueOf(data.getUpper_bound()))
                    increaseBtn.setEnabled(false);
                else
                    increaseBtn.setEnabled(true);
                if (num <= 0)
                    decreaseBtn.setEnabled(false);
                else
                    decreaseBtn.setEnabled(true);

                number_of_people.setText(String.valueOf(num));
            }
        };
        increaseBtn.setOnClickListener(handler);
        decreaseBtn.setOnClickListener(handler);
    }
    public void submit(View v){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            //Not done
        }
        else{
            //Not done
        }
    }

}
