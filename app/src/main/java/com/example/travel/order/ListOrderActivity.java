package com.example.travel.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.travel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListOrderActivity extends AppCompatActivity {
    private String orderID;
    private String userName = "testuser";
    private List<Order> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);
        orderID = getIntent().getStringExtra("orderID");
        getOrderList();
    }
    private void setRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new OrderAdapter(orderList, this);
        recyclerView.setAdapter(mAdapter);
    }
    public void noorder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListOrderActivity.this);
        builder.setMessage("找不到訂單")
                .setTitle("錯誤");
        builder.setPositiveButton("回到上一頁", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                intent = new Intent(ListOrderActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void getOrderList(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(orderID.equals("")){
            db.collection("order")
                    .whereEqualTo("user", userName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> orderdata = document.getData();
                                    Order order = new Order();
                                    order.travel_code = orderdata.get("travelcode").toString();
                                    order.documentID = document.getId();
                                    orderList.add(order);
                                    Log.v("tag", orderdata.toString());
                                }
                                if(orderList.size() == 0){
                                    noorder();
                                }
                                setRecyclerView();
                            } else {
                                Log.d("tag", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        else{
            db.collection("order")
                    .whereEqualTo("user", userName)
                    .whereEqualTo("orderID", orderID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> orderdata = document.getData();
                                    Order order = new Order();
                                    order.travel_code = orderdata.get("travelcode").toString();
                                    order.documentID = document.getId();
                                    orderList.add(order);
                                    //Log.v("tag", String.valueOf(orderList.size()));
                                }
                                setRecyclerView();
                            } else {
                                Log.d("tag", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }
}
