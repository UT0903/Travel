package com.example.travel.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.travel.R;

public class OrderActivity extends AppCompatActivity {
    private EditText code;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }
    public void search_all(View view){
        intent = new Intent(OrderActivity.this, ListOrderActivity.class);
        intent.putExtra("orderID", "");
        startActivity(intent);
        return;
    }
    public void search_by_code(View view){
        intent = new Intent(OrderActivity.this, ListOrderActivity.class);
        intent.putExtra("orderID", code.getText().toString());
        startActivity(intent);
        return;
    }
}
