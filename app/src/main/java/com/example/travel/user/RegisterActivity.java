package com.example.travel.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.MyAppCompatActivity;
import com.example.travel.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends MyAppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void jumpLogin(View view){
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    public void jumSignUp(View view){
        intent = new Intent(RegisterActivity.this, SignUpActivity.class);
        finish();
        startActivity(intent);
    }
}
