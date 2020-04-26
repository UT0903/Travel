package com.example.travel.searchTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travel.Api;
import com.example.travel.R;
import com.example.travel.TripInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ListAvaliableActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_avaliable);
        //get info from SearchAvailableActivity.java
        Intent it = getIntent();
        String dest = it.getStringExtra("dest");
        String date = it.getStringExtra("date");
        //Toast.makeText(this, "dest = " + dest + "date =" + date + "=", Toast.LENGTH_LONG).show();
        String code = Api.mapNameToCode(this, dest);
        queryDatabase(code);
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }

    //Query database via async
    public void queryDatabase(String code){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("data")
                .whereEqualTo("travel_code", code)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<TripInfo> result = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> res = document.getData();
                                String title = res.get("title").toString();
                                String travel_code = res.get("travel_code").toString();
                                String start_date = res.get("start_date").toString();
                                String end_date = res.get("end_date").toString();
                                String lower_bound = res.get("lower_bound").toString();
                                String upper_bound = res.get("upper_bound").toString();
                                String price = res.get("price").toString();
                                TripInfo obj = new TripInfo(title, travel_code, start_date,
                                        end_date, lower_bound, upper_bound, price, R.drawable.test);
                                result.add(obj);
                            }
                            getImageFromCloud(result);
                            //build RecycleView

                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                        //hide loading icon
                        View prog = findViewById(R.id.progress_circle);
                        prog.setVisibility(View.GONE);
                    }
                });
    }
    public void getImageFromCloud(ArrayList<TripInfo> result){
        /*StorageReference storageRef = storage.getReference().child();

        StorageReference islandRef = storageRef.child("images/island.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });*/



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(new TripInfoAdapter(getBaseContext(), result));
    }
}


