package com.example.travel.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travel.GlobalVariable;
import com.example.travel.MyAppCompatActivity;
import com.example.travel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListMyOrderActivity extends MyAppCompatActivity {
    GlobalVariable gv;
    ImageView noOrder;
    ArrayList<Map<String, Object>> order;
    DocumentReference documentRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_order);
        noOrder = findViewById(R.id.list_my_order_noOrder);
        gv = (GlobalVariable) getApplicationContext();
        documentRef = db.collection("user").document(gv.documentReference);
        documentRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(!task.isSuccessful()){
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                        else{
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                order = (ArrayList<Map<String, Object>>) document.get("order");
                                if (order.size() == 0) {
                                    noOrder.setVisibility(View.VISIBLE);
                                } else {
                                    //build RecycleView
                                    RecyclerView recyclerView = findViewById(R.id.list_my_order_recyclerView);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                    recyclerView.setAdapter(new OrderInfoAdapter(getBaseContext(), order));
                                }
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        }
                    }
                });

    }

}
