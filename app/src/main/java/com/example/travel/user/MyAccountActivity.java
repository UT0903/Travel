package com.example.travel.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.MainActivity;
import com.example.travel.R;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signOut(View view){
        mAuth.signOut();
        intent = new Intent(MyAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
