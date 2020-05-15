package com.example.travel.searchTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travel.MyAppCompatActivity;
import com.example.travel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ListAvailableActivity extends MyAppCompatActivity {
    private String selectedCode;
    private String selectedItem;
    private TextView selected_item;
    private EditText start_date_text, end_date_text, number_of_people;

    private View progress_circle;
    private ArrayList<Map<String, Object>> searchResult = new ArrayList<Map<String, Object>>();
    private ArrayList<Map<String, Object>> copyOfSearchResult = new ArrayList<Map<String, Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_available);
        //get info from SearchAvailableActivity.java
        Intent it = getIntent();
        selectedCode = it.getStringExtra("selectedCode");
        selectedItem = it.getStringExtra("selectedItem");
        selected_item = (TextView) findViewById(R.id.selected_item);
        start_date_text = (EditText) findViewById(R.id.start_date_text);
        end_date_text = (EditText) findViewById(R.id.end_date_text);
        number_of_people = (EditText) findViewById(R.id.number_of_people);

        progress_circle = (View) findViewById(R.id.progress_circle);

        selected_item.setText(selectedItem);

        start_date_text.addTextChangedListener(watcher);
        end_date_text.addTextChangedListener(watcher);
        number_of_people.addTextChangedListener(watcher);

        //Toast.makeText(this, "selectedCode = " + selectedCode + "selectedItem =" + selectedItem + "=", Toast.LENGTH_LONG).show();
        queryDatabase();


    }

    //Query database via async
    public void queryDatabase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("data")
                .whereEqualTo("travel_code", selectedCode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> res = document.getData();
                                res.put("picture", R.drawable.test);
                                searchResult.add(res);
                                copyOfSearchResult.add(res);
                            }
                            //build RecycleView
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_available_recyclerView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerView.setAdapter(new TripInfoAdapter(getBaseContext(), copyOfSearchResult));
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                        //hide loading icon
                        progress_circle.setVisibility(View.GONE);
                    }
                });
    }

    private Date startDate, endDate;
    private int nowDate;
    public void datePicker(View v) {
        switch (v.getId()) {
            case (R.id.start_date_text):
                nowDate = 0;
                break;
            case (R.id.end_date_text):
                nowDate = 1;
                break;
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //a bug in SDK that can be solved by adding 1 to month

                if (nowDate == 0) {
                    startDate = c.getTime();
                    start_date_text.setText(sdf.format(c.getTime()));
                } else {
                    endDate = c.getTime();
                    end_date_text.setText(sdf.format(c.getTime()));
                }
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(v.getContext(), datePickerListener, year, month, day);
        if (nowDate == 0 && endDate != null)
            dialog.getDatePicker().setMaxDate(endDate.getTime());
        if (nowDate == 1 && startDate != null)
            dialog.getDatePicker().setMinDate(startDate.getTime());
        dialog.show();
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //progress_circle.setVisibility(View.VISIBLE);
            copyOfSearchResult.clear();
            String _start_date_text = start_date_text.getText().toString().trim();
            String _end_date_text = end_date_text.getText().toString().trim();
            String _number_of_people = number_of_people.getText().toString().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(int i = 0; i < searchResult.size(); i++){
                if(_start_date_text.compareTo("") != 0) {
                    try {
                        Date sd1 = sdf.parse(_start_date_text);
                        Date sd2 = sdf.parse(searchResult.get(i).get("start_date").toString());
                        if(sd1.compareTo(sd2) > 0) continue;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(_end_date_text.compareTo("") != 0){
                    try {
                        Date sd1 = sdf.parse(_end_date_text);
                        Date sd2 = sdf.parse(searchResult.get(i).get("end_date").toString());
                        if(sd1.compareTo(sd2) < 0) continue;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(_number_of_people.compareTo("") != 0 &&
                        Integer.valueOf(_number_of_people) > Integer.valueOf(searchResult.get(i).get("upper_bound").toString())){
                    continue;
                }
                copyOfSearchResult.add(searchResult.get(i));
            }
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_available_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView.setAdapter(new TripInfoAdapter(getBaseContext(), copyOfSearchResult));
        }
    };
}