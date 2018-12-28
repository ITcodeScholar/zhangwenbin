package com.example.xyxy001.weatherforecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    MyApp mp ;
    ListView district_LV;
    List<String> districts;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        district_LV=findViewById(R.id.district_view);
        mp=(MyApp) getApplication();
        String key = getIntent().getStringExtra("city");
        districts= (List<String>) mp.getDistrict_data().get(key);
        //Log.d("1111", String.valueOf(districts.size()));
        adapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.abc,districts);
        district_LV.setAdapter(adapter);

        district_LV.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent();
                intent.setClass(getApplicationContext(),MainActivity.class);
                mp.setNeed_data(districts.get(i));
                Log.d("test","---------------"+mp.getNeed_data());
                startActivity(intent);
            }
        });
    }
}
