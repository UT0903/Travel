package com.example.travel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyAppCompatActivity extends AppCompatActivity {
    public GlobalVariable gv;
    private Button btn;
    private ProgressBar circle;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void startSearch(ProgressBar circle, Button btn){
        circle.setVisibility(View.VISIBLE);
        btn.setEnabled(false);
    }
    public void initSearch(ProgressBar circle, Button btn){
        circle.setVisibility(View.GONE);
        btn.setEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gv = (GlobalVariable)getApplicationContext();
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
}
