package com.example.travel.searchTrip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travel.R;

public class SearchAvailableActivity extends AppCompatActivity {
    private static final int REQ_FROM_A = 1;
    private static final int RESULT_B = 1;
    private EditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_available);
        editDate = (EditText) findViewById(R.id.search_available_date);
    }

    private void showMessage(String message) {
        LayoutInflater factory = getLayoutInflater();
        View view = factory.inflate(R.layout.show_error_msg, null);
        TextView textView = (TextView) view.findViewById(R.id.error_msg);
        textView.setText(message);
        Dialog dialog = new Dialog(SearchAvailableActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }

    public void search(View v) {
        EditText aval_dest = (EditText) findViewById(R.id.search_available_dest);
        EditText aval_date = (EditText) findViewById(R.id.search_available_date);
        String dest = aval_dest.getText().toString().trim();
        String date = aval_date.getText().toString().trim();
        if(dest.compareTo("") == 0){
            showMessage("請輸入目的地");
            return;
        }
        else {
            Intent it = new Intent(SearchAvailableActivity.this, ListAvailableActivity.class);
            it.putExtra("dest", dest);
            it.putExtra("date", date);
            startActivity(it);
        }
    }

    public void regionPicker(View v) {
        Intent it = new Intent(SearchAvailableActivity.this, PickRegionActivity.class);
        startActivityForResult(it, REQ_FROM_A);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_B && requestCode == REQ_FROM_A) {
            String result = data.getExtras().getString("item");
            EditText dest = (EditText) findViewById(R.id.search_available_dest);
            dest.setText(result);
        }
    }



}
