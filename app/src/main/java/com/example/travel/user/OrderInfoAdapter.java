package com.example.travel.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.R;
import com.example.travel.searchTrip.MoreTripInfoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Modify RecyclerView Adapter
public class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Map<String, Object>> list;

    OrderInfoAdapter(Context context, ArrayList<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public OrderInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_my_order_cardview, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(OrderInfoAdapter.ViewHolder holder, int position) {
        final HashMap<String, Object> list = (HashMap<String, Object>) this.list.get(position);
        holder.title.setText(String.valueOf(list.get("title").toString()));
        String date = list.get("start_date") + "~" + list.get("end_date");
        holder.date.setText(date);
        holder.price.setText(list.get("price").toString());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            //Set onClick event to go to MoreTripInfoActivity.java
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, MoreTripInfoActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra("data", list);
                context.startActivity(it);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, date, price, numOfPeople;
        Button btn;
        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.myOrder_title);
            date = (TextView) itemView.findViewById(R.id.myOrder_date);
            price = (TextView) itemView.findViewById(R.id.myOrder_price);
            numOfPeople = (TextView) itemView.findViewById(R.id.myOrder_numOfPeople);
            btn = (Button) itemView.findViewById(R.id.myOrder_btn);
        }
    }
}

