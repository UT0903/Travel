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

//Modify RecyclerView Adapter in ListAvailableActivity
public class TripInfoAdapter extends RecyclerView.Adapter<TripInfoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Map<String, Object>> trip_list;
    //Constructor
    TripInfoAdapter(Context context, ArrayList<Map<String, Object>> trip_list) {
        this.context = context;
        this.trip_list = trip_list;
    }

    @Override
    //Override onCreateViewHolder to satisfy recycle view requirement.
    public TripInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_available_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //Override onBindHolder to satisfy recycle view requirement.
    public void onBindViewHolder(TripInfoAdapter.ViewHolder holder, int position) {
        final HashMap<String, Object> list = (HashMap<String, Object>) trip_list.get(position);
        holder.imageId.setImageResource((int)list.get("picture"));
        holder.titleId.setText("Topic: " + list.get("title").toString());
        String date = list.get("start_date") + "~" + list.get("end_date");
        holder.dateId.setText("Date: " + date);
        holder.priceId.setText("Price: " + list.get("price").toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //Set onClick event to go to MoreTripInfoActivity.java
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MoreTripInfoActivity.class);
                intent.putExtra("data", list);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trip_list.size();
    }

    //Adapter need a ViewHolder. We only implement constructor.
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
