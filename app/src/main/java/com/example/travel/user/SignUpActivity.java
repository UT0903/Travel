package com.example.travel.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends MyAppCompatActivity {
    private EditText email, password, name, passwordDouble, phone;
    private Intent intent;
    private Button btn;
    private ProgressBar circle;
    private Query Ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        name = (EditText)findViewById(R.id.name);
        passwordDouble = (EditText)findViewById(R.id.passwordDouble);
        phone = (EditText)findViewById(R.id.phone);
        btn = (Button) findViewById(R.id.signUp_btn);
        circle = (ProgressBar) findViewById(R.id.signUp_progress_circle);
        initSearch(circle, btn);
    }
    public void SignUp(View view){
        Log.v("SignUp", "SignUp");
        String name = this.name.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String passwordDouble = this.passwordDouble.getText().toString().trim();
        String phone = this.phone.getText().toString().trim();
        if(dataCheck(name, email, password, passwordDouble, phone) == -1) return;
        final Map<String, Object> data = toMap(name, email, password, phone, Arrays.asList());
        startSearch(circle, btn);
        db.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()){
                                Create_account(circle, btn, data);
                            }
                            else{
                                Toast.makeText(getBaseContext(), "電子郵件已存在", Toast.LENGTH_SHORT).show();
                                initSearch(circle, btn);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            initSearch(circle, btn);
                        }
                    }
                });
        }
    private void Create_account(final ProgressBar circle, final Button btn, final Map<String, Object> data){
        db.collection("user")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.w("TAG", "Add account successfully");
                        gv.isLogin = true; gv.documentReference = documentReference.getId();
                        initSearch(circle, btn);
                        intent = new Intent(SignUpActivity.this, MyAccountActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        initSearch(circle, btn);
                    }
                });

    }
    private int dataCheck(String name, String email, String password, String doublePassword, String phone){
        if(password.compareTo(doublePassword) != 0){
            Toast.makeText(getBaseContext(), "兩次密碼輸入不相同", Toast.LENGTH_SHORT).show();
            return -1;
        }
        //add other rule
        return 1;
    }
    private Map<String, Object> toMap(String name, String email, String password, String phone, List<Object> order){
        Map<String, Object> temp = new HashMap<>();
        temp.put("name", name); temp.put("email", email); temp.put("password", password); temp.put("phone", phone);
        temp.put("order", order);
        return temp;
    }
}
