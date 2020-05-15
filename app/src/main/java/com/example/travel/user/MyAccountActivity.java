package com.example.travel.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.GlobalVariable;
import com.example.travel.MainActivity;
import com.example.travel.MyAppCompatActivity;
import com.example.travel.R;

public class MyAccountActivity extends MyAppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        gv = (GlobalVariable)getApplicationContext();
    }
    public void listMyOrder(View view){
        intent = new Intent(MyAccountActivity.this, ListMyOrderActivity.class);
        startActivity(intent);
    }
    public void Logout(View view){
        gv.isLogin = false;
        gv.documentReference = null;
        intent = new Intent(MyAccountActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
