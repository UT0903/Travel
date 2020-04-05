package com.example.travel;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;


public class Api {
    private String loadData(Context context){
        String json = null;
        try {
            InputStream is = context.getAssets().open("HotelList.json");
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
    public <T> T[] getTravelCodeName(){
        InputStream is = JsonLearn.this.getClass().getClassLoader().getResourceAsStream("assets/"   "Text.json");
    }
    public String Api.getAvaliableData(){

    }
}
