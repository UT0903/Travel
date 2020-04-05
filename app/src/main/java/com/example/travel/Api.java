package com.example.travel;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Api {
    private static String loadTravelCode(Context context){
        String json = null;
        try {
            InputStream is = context.getAssets().open("travel_code.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public static ArrayList<String> getTravelCodeName(Context context){
        try{
            ArrayList<String> result = new ArrayList();
            JSONArray codeArray = new JSONArray(loadTravelCode(context));
            for(int i = 0; i < codeArray.length(); i++){
                JSONObject parser = codeArray.getJSONObject(i);
                String name = parser.getString("travel_code_name");
                result.add(name);
            }
            return result;
        }
        catch(JSONException e){
            Log.e("HotelList", "Problem parsing the travel code JSON results", e);
            return null;
        }
    }
    public static String mapNameToCode(Context context, String TravelcodeName){
        try{
            JSONArray codeArray = new JSONArray(loadTravelCode(context));
            for(int i = 0; i < codeArray.length(); i++){
                JSONObject parser = codeArray.getJSONObject(i);
                String name = parser.getString("travel_code_name");
                if(TravelcodeName.compareTo(name) == 0){
                    String code = parser.getString("travel_code");
                    return code;
                }
            }
            return null;
        }
        catch(JSONException e){
            Log.e("HotelList", "Problem mapping the travel code JSON results", e);
            return null;
        }
    }



    /*public void search(View view){
        db.collection("data")
                .whereEqualTo("travel_code", et.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("tag", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return;
    }*/
}
