package com.example.travel.searchTrip;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Modify RecyclerView Adapter
public class TripInfoAdapter extends RecyclerView.Adapter<TripInfoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Map<String, Object>> trip_list;

    TripInfoAdapter(Context context, ArrayList<Map<String, Object>> trip_list) {
        this.context = context;
        this.trip_list = trip_list;
    }

    @Override
    public TripInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_available_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripInfoAdapter.ViewHolder holder, int position) {
        final HashMap<String, Object> list = (HashMap<String, Object>) trip_list.get(position);
        holder.imageId.setImageResource((int)list.get("picture"));
        holder.titleId.setText(String.valueOf(list.get("title").toString()));
        String date = list.get("start_date") + "~" + list.get("end_date");
        holder.dateId.setText(date);
        holder.priceId.setText(list.get("price").toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //Set onClick event to go to MoreTripInfoActivity.java
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, MoreTripInfoActivity.class);
                it.putExtra("data", list);
                //it.setFlags(it.FLAG_ACTIVITY_NEW_TASK);
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
