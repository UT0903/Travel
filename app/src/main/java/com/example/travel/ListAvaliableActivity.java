package com.example.travel;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    }

    //Query database via asyc
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

//Modify RecyclerView Adapter
class TripInfoAdapter extends RecyclerView.Adapter<TripInfoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TripInfo> trip_list;

    TripInfoAdapter(Context context, ArrayList<TripInfo> trip_list) {
        this.context = context;
        this.trip_list = trip_list;
    }

    @Override
    public TripInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_cardview_available, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripInfoAdapter.ViewHolder holder, int position) {
        final TripInfo list = trip_list.get(position);
        holder.imageId.setImageResource(list.getImage());
        holder.titleId.setText(String.valueOf(list.getTitle()));
        String date = list.getStart_date() + "~" + list.getEnd_date();
        holder.dateId.setText(date);
        holder.priceId.setText(list.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //Set onClick event to go to MoreTripInfoActivity.java
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, MoreTripInfoActivity.class);
                it.putExtra("data", list);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trip_list.size();
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageId;
        TextView titleId, dateId, priceId;
        ViewHolder(View itemView) {
            super(itemView);
            imageId = (ImageView) itemView.findViewById(R.id.imageId);
            titleId = (TextView) itemView.findViewById(R.id.titleId);
            dateId = (TextView) itemView.findViewById(R.id.dateId);
            priceId = (TextView) itemView.findViewById(R.id.priceId);

        }
    }
}
