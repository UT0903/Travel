package com.example.travel.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.travel.GlobalVariable;
import com.example.travel.MainActivity;
import com.example.travel.MyAppCompatActivity;
import com.example.travel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class LoginActivity extends MyAppCompatActivity {
    private TextInputLayout email, password;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        btn = findViewById(R.id.login_btn);
        initSearch(btn);
    }

    public void Login(View view){
        String email = this.email.getEditText().getText().toString().trim();
        String password = this.password.getEditText().getText().toString().trim();
        if(!emailCheck(email) || !passwordCheck(password)) return;
        startSearch(btn);
        db.collection("user")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 1) {
                                Toast.makeText(getBaseContext(), "登入失敗，請確認帳號密碼是否正確", Toast.LENGTH_SHORT).show();
                                initSearch(btn);
                            }
                            else{
                                Log.d("Login", "Login Success!");
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    gv.isLogin = true;
                                    gv.documentReference = document.getId();
                                    gv.personalInfo = document.getData();
                                    initSearch(btn);
                                    Intent intent = new Intent(getBaseContext(), MyAccountActivity.class);
                                    finish();
                                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                            initSearch(btn);
                        }
                    }
                });

    }
    private boolean emailCheck(String email){
        if (email.compareTo("") == 0) {
            this.email.setError("此欄位必填");
            return false;
        }
        this.email.setError(null);
        return true;
    }
    private boolean passwordCheck(String password){
        if (password.compareTo("") == 0) {
            this.password.setError("此欄位必填");
            return false;
        }
        this.password.setError(null);
        return true;
    }

}
