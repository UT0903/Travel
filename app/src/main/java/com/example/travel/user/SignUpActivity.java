package com.example.travel.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.travel.GlobalVariable;
import com.example.travel.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText email, password, name, passwordDouble, phone;
    private ProgressBar progress_circle;
    private Intent intent;
    GlobalVariable gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        name = (EditText)findViewById(R.id.name);
        passwordDouble = (EditText)findViewById(R.id.passwordDouble);
        phone = (EditText)findViewById(R.id.phone);
        gv = (GlobalVariable)getApplicationContext();
        progress_circle = (ProgressBar) findViewById(R.id.signUp_progress_circle);
        progress_circle.setVisibility(View.INVISIBLE);
    }
    private long mLastClickTime = 0;
    public void SignUp(View view){
        Log.v("SignUp", "SignUp");
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        gv.SignUp(name.getText().toString().trim(), email.getText().toString().trim(), password.getText().toString().trim(), passwordDouble.getText().toString().trim(), phone.getText().toString().trim(), progress_circle);
    }
}
