package com.example.travel.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
    }

    public void jumpLogin(View view){
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void jumSignUp(View view){
        intent = new Intent(RegisterActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
