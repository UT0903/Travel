package com.example.travel.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.travel.MainActivity;
import com.example.travel.MyAppCompatActivity;
import com.example.travel.R;

public class MyAccountActivity extends MyAppCompatActivity {
    private TextView name, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        name = findViewById(R.id.my_account_name);
        email = findViewById(R.id.my_account_email);
        //Log.e("MyAccount", gv.personalInfo.get("name").toString());
        name.setText(gv.personalInfo.get("name").toString());
        email.setText(gv.personalInfo.get("email").toString());
    }
    public void listMyOrder(View view){
        Intent intent = new Intent(MyAccountActivity.this, ListMyOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void Logout(View view){
        gv.isLogin = false;
        gv.documentReference = null;
        Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }
}
