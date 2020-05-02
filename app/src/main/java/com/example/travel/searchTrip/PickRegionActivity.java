package com.example.travel.searchTrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.travel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PickRegionActivity extends AppCompatActivity {
    ArrayList<String> str = new ArrayList<String>();
    Map<String, String> map = new HashMap<String, String>();
    ArrayAdapter adapter;
    SearchView searchView;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_region);
        lv = (ListView) findViewById(R.id.list_view);
        getTravelCodeName(this);
        searchView = (SearchView) findViewById(R.id.searchView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, str);
        lv.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String text){
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                String selectedCode = map.get(selectedItem);
                Intent it = new Intent(PickRegionActivity.this, ListAvailableActivity.class);
                it.putExtra("selectedCode", selectedCode);
                it.putExtra("selectedItem", selectedItem);
                startActivity(it);
            }
        });
    }
    private void getTravelCodeName(Context context){
        try{
            JSONArray codeArray = new JSONArray(loadTravelCode(context));
            for(int i = 0; i < codeArray.length(); i++){
                JSONObject parser = codeArray.getJSONObject(i);
                String name = parser.getString("travel_code_name");
                String travel_code = parser.getString("travel_code");
                String[] nameArr = name.split("．");
                for(int j = 0; j < nameArr.length; j++){
                    str.add(nameArr[j]);
                    map.put(nameArr[j], travel_code);
                }
            }
            return;
        }
        catch(JSONException e){
            Log.e("HotelList", "Problem parsing the travel code JSON results", e);
            return;
        }
    }
    private String loadTravelCode(Context context){
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



}

