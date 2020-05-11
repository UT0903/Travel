package com.example.travel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.travel.user.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class GlobalVariable extends Application {
    public String Email = "", Name = "", Phone = "", Password = "";
    private ArrayList<Map> order;
    public boolean isLogin;


    public void SignUp(final String name, final String email, final String password, final String doublePassword, final String phone, final ProgressBar progress_circle) {
        dataCheck(name, email, password, doublePassword, phone);
        progress_circle.setVisibility((View.VISIBLE));
        Log.e("TAG", "here");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()){
                                Create_account(name, email, password, phone, progress_circle);
                            }
                            else{
                                Toast.makeText(getBaseContext(), "電子郵件已存在", Toast.LENGTH_SHORT).show();
                                progress_circle.setVisibility((View.GONE));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void Create_account(final String name, final String email, final String password, final String phone, final ProgressBar progress_circle){
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("password", password);
        data.put("phone", phone);
        data.put("order", Arrays.asList());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.w("TAG", "Add account successfully");
                        isLogin = true;
                        Email = new String(email);
                        Name = new String(name);
                        Phone = new String(phone);
                        Password = new String(password);
                        progress_circle.setVisibility((View.GONE));
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        progress_circle.setVisibility((View.GONE));
                    }
                });

    }
    private void dataCheck(final String name, final String email, final String password, final String doublePassword, final String phone){
        if(password.compareTo(doublePassword) != 0){
            Toast.makeText(getBaseContext(), "兩次密碼輸入不相同", Toast.LENGTH_SHORT).show();
            return;
        }
        // other rule
    }
}