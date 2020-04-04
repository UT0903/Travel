package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class SearchAvailableActivity extends AppCompatActivity {
    private EditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_available);
        editDate = (EditText)findViewById(R.id.search_available_date);
    }

    /*private void showMessage(String message) {
        LayoutInflater factory = getLayoutInflater();
        View view = factory.inflate(R.layout.failed_search_available, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_search_available_text);
        textView.setText(message);
        Dialog dialog = new Dialog(SearchAvailableActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }*/

    public void search(View v) {
        /*Intent intent = new Intent(SearchAvailableActivity.this, SearchAvailableListActivity.class);
        intent.putExtra("Hotel_list", result);
        startActivity(intent);*/
    }




    public void datePicker(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String dateTime = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
                editDate.setText(dateTime);
            }
        }, year, month, day).show();
    }
}
