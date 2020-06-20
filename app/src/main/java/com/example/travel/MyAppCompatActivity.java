package com.example.travel;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
//Override AppCompatActivity in order to add some pre-doing jobs in every activity
public class MyAppCompatActivity extends AppCompatActivity {
    public GlobalVariable gv;
    private Button btn;
    private ProgressBar circle;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    //A start-search function that can be called by every activity
    public void startSearch(Button btn){
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        btn.setEnabled(false);
    }
    //An end-search function that can be called by every activity
    public void initSearch(Button btn){
        btn.setEnabled(true);
    }
    @Override
    //Hide action bar when creating every activity
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gv = (GlobalVariable)getApplicationContext();
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
    @Override
    protected void onStart(){
        super.onStart();
    }
}
