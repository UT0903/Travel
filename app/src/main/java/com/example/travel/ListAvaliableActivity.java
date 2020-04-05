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
        Intent it = getIntent();
        String dest = it.getStringExtra("dest");
        String date = it.getStringExtra("date");
        //Toast.makeText(this, "dest = " + dest + "date =" + date + "=", Toast.LENGTH_LONG).show();
        String code = Api.mapNameToCode(this, dest);
        ArrayList <TripInfo> result = new ArrayList<>();

        TripInfo a = new TripInfo();
        a.setImage(R.drawable.test);
        result.add(a);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // MemberAdapter 會在步驟7建立
        recyclerView.setAdapter(new TripInfoAdapter(this, result));

        //queryDatabase(code);
    }

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
                                        end_date, lower_bound, upper_bound, price);
                                result.add(obj);
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                        //hideLoading();
                    }
                });
    }

    /*prog = findViewById(R.id.progressCircle);
    public void showLoading(){
        prog.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        prog.setVisibility(View.GONE);
    }*/
}

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
        holder.imageId.setImageResource(list.image);
        //holder.titleId.setText(String.valueOf(list.title));
        //String date = list.start_date + "~" + list.end_date;
        //holder.dateId.setText(date);
        //holder.priceId.setText(list.price);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(list.getImage());
                Toast toast = new Toast(context);
                toast.setView(imageView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
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
        //TextView titleId, dateId, priceId;
        ViewHolder(View itemView) {
            super(itemView);
            imageId = (ImageView) itemView.findViewById(R.id.imageId);
            //titleId = (TextView) itemView.findViewById(R.id.titleId);
            //dateId = (TextView) itemView.findViewById(R.id.dateId);
            //priceId = (TextView) itemView.findViewById(R.id.priceId);

        }
    }
}
