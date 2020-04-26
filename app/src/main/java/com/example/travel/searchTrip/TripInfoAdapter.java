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
import com.example.travel.TripInfo;

import java.util.ArrayList;

//Modify RecyclerView Adapter
public class TripInfoAdapter extends RecyclerView.Adapter<TripInfoAdapter.ViewHolder> {
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
