package com.example.travel.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.regex.Pattern;
import com.example.travel.GlobalVariable;
import com.example.travel.MainActivity;
import com.example.travel.MyAppCompatActivity;
import com.example.travel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends MyAppCompatActivity {
    private TextInputLayout email, password, name, passwordDouble, phone;
    private Intent intent;
    private Button btn;
    private ProgressBar circle;
    private boolean nameValid, emailValid, passwordValid, passwordDoubleValid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        passwordDouble = findViewById(R.id.passwordDouble);
        btn = (Button) findViewById(R.id.signUp_btn);
        name.getEditText().addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameCheck(name.getEditText().getText().toString().trim());
            }
        });
        email.getEditText().addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailCheck(email.getEditText().getText().toString().trim());
            }
        });
        password.getEditText().addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordCheck(password.getEditText().getText().toString().trim());
            }
        });
        passwordDouble.getEditText().addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordDoubleCheck(password.getEditText().getText().toString().trim(), passwordDouble.getEditText().getText().toString().trim());
            }
        });
        initSearch(btn);
    }
    public static abstract class MyTextWatcher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    }

    public void SignUp(View view) {
        String name = this.name.getEditText().getText().toString().trim();
        String email = this.email.getEditText().getText().toString().trim();
        String password = this.password.getEditText().getText().toString().trim();
        String passwordDouble = this.passwordDouble.getEditText().getText().toString().trim();
        String phone = this.phone.getEditText().getText().toString().trim();
        if(!nullCheck(name, email, password, passwordDouble) || !nameValid || !emailValid || !passwordValid || !passwordDoubleValid){
            Log.e("TAG", "invalid input");
            return;
        }
        final Map<String, Object> data = toMap(name, email, password, phone, Arrays.asList());
        startSearch(btn);
        db.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Create_account(circle, btn, data);
                            } else {
                                Toast.makeText(getBaseContext(), "電子郵件已存在", Toast.LENGTH_SHORT).show();
                                initSearch(btn);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            initSearch(btn);
                        }
                    }
                });
    }

    private void Create_account(final ProgressBar circle, final Button btn, final Map<String, Object> data) {
        db.collection("user")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.w("TAG", "Add account successfully");
                        gv.isLogin = true;
                        gv.documentReference = documentReference.getId();
                        gv.personalInfo = data;
                        initSearch(btn);
                        intent = new Intent(SignUpActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        initSearch(btn);
                    }
                });
    }
    private boolean nullCheck(String name, String email, String password, String passwordDouble){
        boolean check = true;
        if(name.equals("")){
            this.name.setError("此欄位必填");
            check = false;
        }
        if(email.equals("")){
            this.email.setError("此欄位必填");
            check = false;
        }
        if(password.equals("")){
            this.password.setError("此欄位必填");
            check = false;
        }
        if(passwordDouble.equals("")){
            this.passwordDouble.setError("此欄位必填");
            check = false;
        }
        return check;
    }
    private void nameCheck(String name){
        if(name.length() > 10){
            this.name.setError("長度超過10");
            nameValid = false;
            return;
        }
        this.name.setError(null);
        nameValid = true;
        return;
    }
    private void emailCheck(String email) {
        if(!email.contains("@")){
            this.email.setError("錯誤的Email格式");
            emailValid = false;
            return;
        }
        this.email.setError(null);
        emailValid = true;
        return;
    }
    private void passwordCheck(String password) {
        if(password.length() < 4){
            this.password.setError("密碼長度不可小於4位");
            passwordValid = false;
            return;
        }
        this.password.setError(null);
        passwordValid = true;
        return;
    }
    private void passwordDoubleCheck(String password, String passwordDouble) {
        if (password.compareTo(passwordDouble) != 0) {
            this.passwordDouble.setError("兩次密碼輸入不相同");
            passwordDoubleValid = false;
            return;
        }
        this.passwordDouble.setError(null);
        passwordDoubleValid = true;
        return;
    }

    private Map<String, Object> toMap(String name, String email, String password, String phone, List<Object> order) {
        Map<String, Object> temp = new HashMap<>();
        temp.put("name", name);
        temp.put("email", email);
        temp.put("password", password);
        temp.put("phone", phone);
        temp.put("order", order);
        return temp;
    }
}
