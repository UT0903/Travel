package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PickRegionActivity extends AppCompatActivity {
    private static final int RESULT_B = 1;
    public String[] str = Api.getTravelCodeName();
    //public String[] str = {"新北市", "台北市", "台中市", "台南市", "高雄市"};
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_region);
        ListView lv = (ListView) findViewById(R.id.list_view);

        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, str);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                Intent it = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("item", selectedItem);
                it.putExtras(bundle);
                setResult(RESULT_B, it);
                PickRegionActivity.this.finish();
            }
        });
    }


}

