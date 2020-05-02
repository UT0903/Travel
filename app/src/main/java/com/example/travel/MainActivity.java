package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travel.searchTrip.PickRegionActivity;
import com.example.travel.user.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

    }
    public void searchAvailable(View v){
        //Toast.makeText(HomeActivity.this, "here", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(MainActivity.this, PickRegionActivity.class);
        startActivity(it);
    }
    public void myAccount(View v){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent it = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(it);
        }
        else{
            //not done yet
        }
    }
    /*public void signOut(View view){
        mAuth.signOut();
        intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }*/

}
