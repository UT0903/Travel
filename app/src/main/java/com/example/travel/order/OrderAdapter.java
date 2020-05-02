package com.example.travel.order;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.MainActivity;
import com.example.travel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private FirebaseFirestore db;
    private List<Order> mDataset;
    private Context context;
    private Intent intent;
    OrderAdapter(List<Order> orderList, Context context){
        mDataset = orderList;
        db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView travalcode;
        public Button button;
        public TextView adult;
        public TextView children;
        public MyViewHolder(View v) {
            super(v);
            travalcode = (TextView) v.findViewById(R.id.travelcode);
            button = (Button) v.findViewById(R.id.deleteorder);
            adult = (TextView)v.findViewById(R.id.adult);
            children = (TextView) v.findViewById(R.id.children);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderitem, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Order order = mDataset.get(position);
        holder.travalcode.setText(order.travel_code);
        holder.adult.setText(order.adultNum);
        holder.children.setText(order.childrenNum);
        holder.button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("是否刪除這個行程")
                        .setTitle("警告：您正在刪除一個行程");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        remove(position);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void remove(int position){
        Log.v("tag", String.valueOf(position));
        Log.v("tag", mDataset.get(position).documentID);
        db.collection("order").document(mDataset.get(position).documentID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("tag", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("tag", "Error deleting document", e);
                    }
                });
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        if(mDataset.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("已經沒有訂單")
                    .setTitle("錯誤");
            builder.setPositiveButton("回到上一頁", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
}
