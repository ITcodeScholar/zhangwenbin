package com.example.xyxy001.weatherforecast;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    MyApp mp ;
    ListView city_LV;
    List<String> citys;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        city_LV=findViewById(R.id.city_view);
        mp=(MyApp) getApplication();
        String key = getIntent().getStringExtra("province");//获取省份名称
        citys= (List<String>) mp.getCity_data().get(key);//根据省份获取城市
        adapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.abc,citys);
        city_LV.setAdapter(adapter);

        city_LV.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent();
                intent.setClass(getApplicationContext(),Main4Activity.class);
                intent.putExtra("city",citys.get(i));
                startActivity(intent);
            }
        });
    }
}
