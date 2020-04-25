package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.order.OrderActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
    }
    public void searchAvailable(View v){
        //Toast.makeText(HomeActivity.this, "here", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(HomeActivity.this, SearchAvailableActivity.class);
        startActivity(it);
    }
    public void orderTrip(View v){
        Intent it = new Intent(HomeActivity.this, OrderTripActivity.class);
        startActivity(it);
    }
    public void queryTrip(View v){
        Intent it = new Intent(HomeActivity.this, OrderActivity.class);
        startActivity(it);
    }

    public void signout(View view){
        mAuth.signOut();
        intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
