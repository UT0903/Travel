package com.example.travel;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.searchTrip.PickRegionActivity;
import com.example.travel.user.MyAccountActivity;
import com.example.travel.user.RegisterActivity;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    //When clicking button "Search Trip" will be directed to PickRegionActivity
    public void searchAvailable(View v){
        Intent it = new Intent(MainActivity.this, PickRegionActivity.class);
        startActivity(it);
    }
    //When clicking button "My account" will be directed to MyAccountActivity
    public void myAccount(View v){
        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        if(gv.isLogin == false){
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
            startActivity(intent);
        }
    }
}
