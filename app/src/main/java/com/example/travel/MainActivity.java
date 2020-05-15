package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.searchTrip.PickRegionActivity;
import com.example.travel.user.MyAccountActivity;
import com.example.travel.user.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends MyAppCompatActivity {
    private FirebaseUser user;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void searchAvailable(View v){
        //Toast.makeText(HomeActivity.this, "here", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(MainActivity.this, PickRegionActivity.class);
        startActivity(it);
    }
    public void myAccount(View v){
        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        if(gv.isLogin == false){
            Intent it = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(it);
        }
        else{
            Intent it = new Intent(MainActivity.this, MyAccountActivity.class);
            startActivity(it);
        }
    }
}
