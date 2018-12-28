package com.example.xyxy001.weatherforecast;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//获取城市ID
public class Main2Activity extends AppCompatActivity {


    final String TAG="Main2Activity";
    MyApp ma;
    List<String> row_data;
    List<String> province_name;
    Handler handler;
    HashMap<String, List<String>>  d_data ;
    HashMap<String, List<String>>  c_data ;
    ListView province_LV;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        province_LV=findViewById(R.id.province_view);
        province_LV.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),Main3Activity.class);
                intent.putExtra("province",province_name.get(position));
                ma.setProvince(province_name.get(position));
                startActivity(intent);
            }
        });

        d_data = new HashMap<>();
        c_data = new HashMap<>();
        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0x123){
                    for (int i=0;i<row_data.size();i+=4){
                        String city = row_data.get(i+2);
                        List<String> cc = d_data.get(city);
                        if(cc==null){
                            d_data.put(city, new ArrayList<String>());
                            cc = d_data.get(city);
                        }
                        cc.add(row_data.get(i+3));
                    }

                    for (int i=0;i<row_data.size();i+=4){
                        String province = row_data.get(i+1);
                        List<String> pp = c_data.get(province);
                        if (pp==null){
                            c_data.put(province,new ArrayList<String>());
                            pp=c_data.get(province);
                        }
                        if (!pp.contains(row_data.get(i+2)))
                        pp.add(row_data.get(i+2));
                    }
                        ma = (MyApp) getApplication();
                        ma.setCity_data(c_data);
                        ma.setDistrict_data(d_data);
                        ma.setRow_data(row_data);

                        adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.abc,province_name);
                        province_LV.setAdapter(adapter);

                    }
                }
        };



        //获取支持的城市列表
        OkHttpClient client = new OkHttpClient();
        String url ="http://v.juhe.cn/weather/citys?key=c3ff0828efe6c003b83c657919373e2b";
        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()){
                    throw  new IOException("");
                }else {
                    String jsonStr = response.body().string();
                    if (jsonStr!=null){
                        try {
                            row_data = new ArrayList<>();
                            province_name = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            JSONArray result = jsonObject.getJSONArray("result");
                            for (int i=0;i<result.length();i++){
                                JSONObject o =result.getJSONObject(i);
                                row_data.add(o.getString("id"));
                                row_data.add(o.getString("province"));
                                if (!province_name.contains(o.getString("province"))){province_name.add(o.getString("province"));}
                                row_data.add(o.getString("city"));
                                row_data.add(o.getString("district"));
                            }
                            Message m = new Message();
                            m.what = 0x123;
                            handler.sendMessage(m);


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

            }
        });





    }
}
