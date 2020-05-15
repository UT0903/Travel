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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends MyAppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button btn;
    private ProgressBar circle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.login_email);
        password = (EditText)findViewById(R.id.password);
        btn = (Button) findViewById(R.id.login_btn);
        circle = (ProgressBar) findViewById(R.id.login_progress_circle);
        initSearch(circle, btn);
    }

    public void Login(View view){
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        if(dataCheck(email, password) == -1) return;
        startSearch(circle, btn);
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
                                initSearch(circle, btn);
                            }
                            else{
                                Log.d("Login", "Login Success!");
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    gv.isLogin = true;
                                    gv.documentReference = document.getId();
                                    initSearch(circle, btn);
                                    Intent intent = new Intent(getBaseContext(), MyAccountActivity.class);
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                            initSearch(circle, btn);
                        }
                    }
                });

    }
    private int dataCheck(final String email, final String password){
        if (email.compareTo("") == 0 || password.compareTo("") == 0) {
            Log.d("TAG", "failed");
            Toast.makeText(getBaseContext(), "登入失敗，email或密碼不能為空", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
        //add other rule
    }

}
