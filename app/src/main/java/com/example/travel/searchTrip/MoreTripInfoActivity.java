package com.example.travel.searchTrip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travel.MainActivity;
import com.example.travel.MyAppCompatActivity;
import com.example.travel.R;
import com.example.travel.user.ListMyOrderActivity;
import com.example.travel.user.MyAccountActivity;
import com.example.travel.user.RegisterActivity;
import com.example.travel.user.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MoreTripInfoActivity extends MyAppCompatActivity {
    private TextView number_of_people, title, start_date, end_date;
    private Button increaseBtn, decreaseBtn, submitBtn, deleteBtn;
    private HashMap<String, Object> data;
    private int num = 1, remain, myOrderNum;
    private Intent intent;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query dataRef, personalInfoRef;
    private DocumentReference documentRef;
    private boolean modify;
    private ImageView im;
    @Override
    //Set up all view in data passed from ListAvailableActivity.
    //This activity will show when enter from either ListAvailableActivity (to order the trip)
    //or ListMyOrderActivity (to modify or delete the trip)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_more_trip_info);
        data = (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        if(data.containsKey("numOfPeople")) modify = true;
        dataRef = db.collection("data")
                .whereEqualTo("title", data.get("title").toString())
                .whereEqualTo("travel_code", data.get("travel_code").toString())
                .whereEqualTo("product_key", data.get("product_key").toString())
                .whereEqualTo("start_date", data.get("start_date").toString());
        title = (TextView) findViewById(R.id.trip_title);
        title.setText(data.get("title").toString());
        submitBtn = (Button) findViewById(R.id.moreTripInfo_submitBtn);
        deleteBtn = findViewById(R.id.moreTripInfo_deleteBtn);
        start_date = (TextView) findViewById(R.id.trip_start_date);
        start_date.setText(data.get("start_date").toString());
        end_date = (TextView) findViewById(R.id.trip_end_date);
        end_date.setText(data.get("end_date").toString());
        im = findViewById(R.id.iv_1);
        im.setImageResource(Integer.parseInt(data.get("picture").toString()));
        number_of_people = (TextView) findViewById(R.id.trip_num_of_people);
        increaseBtn = (Button) findViewById(R.id.trip_increase);
        decreaseBtn = (Button) findViewById(R.id.trip_decrease);
        if(modify){
            submitBtn.setText("Modify");
            myOrderNum = Integer.parseInt(data.get("numOfPeople").toString());
            num = myOrderNum;
            number_of_people.setText(String.valueOf(myOrderNum));
        }
        else {
            myOrderNum = 0;
            deleteBtn.setVisibility(View.GONE);
        }
        if(myOrderNum <= 1) decreaseBtn.setEnabled(false);
        number_of_people.setText(String.valueOf(num));

        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case (R.id.trip_decrease):
                        num--;
                        break;
                    case (R.id.trip_increase):
                        num++;
                        break;
                }
                if (num >= remain + myOrderNum)
                    increaseBtn.setEnabled(false);
                else
                    increaseBtn.setEnabled(true);
                if (num <= 1)
                    decreaseBtn.setEnabled(false);
                else
                    decreaseBtn.setEnabled(true);

                number_of_people.setText(String.valueOf(num));
            }
        };
        increaseBtn.setOnClickListener(handler);
        decreaseBtn.setOnClickListener(handler);
    }
    //Add snap shot listener to listen from FireStore whenever the data being changed by others, thus to prevent race condition.
    @Override
    protected void onStart() {
        super.onStart();
        dataRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("TAG", "Listen failed.", e);
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            documentRef = db.document("data/" + doc.getId());
                            remain = Integer.parseInt(doc.getString("upper_bound"));
                            Log.d("Important", "now remain: "+ String.valueOf(remain));
                            submitBtn.setEnabled(true);
                        }
                    }
                });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    //OnClick the Send button to submit the (modified) order into the FireStore
    public void submit(View view){
        if(gv.isLogin){
            if(myOrderNum + remain < num){
                Toast.makeText(MoreTripInfoActivity.this, "可預定數目不足", Toast.LENGTH_SHORT).show();
            }
            else {
                if(modify){
                    remain = remain + myOrderNum - num;
                    documentRef.update("upper_bound", String.valueOf(remain));
                    db.collection("user")
                            .document(gv.documentReference)
                            .update("order", FieldValue.arrayRemove(data));
                    HashMap<String, Object> newData = new HashMap<>(data);
                    newData.replace("numOfPeople", num);
                    newData.replace("orderTime", new Date());
                    db.collection("user")
                            .document(gv.documentReference)
                            .update("order", FieldValue.arrayUnion(newData));
                    Log.d("TAG", "modify successful");
                    intent = new Intent(MoreTripInfoActivity.this, ListMyOrderActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                    startActivity(intent);
                }
                else{
                    remain -= num;
                    documentRef.update("upper_bound", String.valueOf(remain));
                    HashMap<String, Object> newData = new HashMap<>(data);
                    newData.put("numOfPeople", num);
                    newData.put("orderTime", new Date());
                    db.collection("user")
                            .document(gv.documentReference)
                            .update("order", FieldValue.arrayUnion(newData));
                    Log.d("TAG", "update successful");
                    intent = new Intent(MoreTripInfoActivity.this, ListMyOrderActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        }
        else{
            //Not login yet, redirected to RegisterActivity first
            Toast.makeText(MoreTripInfoActivity.this, "Please Log in First", Toast.LENGTH_SHORT).show();
            intent = new Intent(MoreTripInfoActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
    //OnClick the delete button to delete the order from FireStore
    public void delete(View view){
        documentRef.update("upper_bound", String.valueOf(remain + myOrderNum));
        db.collection("user")
                .document(gv.documentReference)
                .update("order", FieldValue.arrayRemove(data));
        intent = new Intent(MoreTripInfoActivity.this, ListMyOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

}
