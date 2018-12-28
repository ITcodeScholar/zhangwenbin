package com.example.xyxy001.weatherforecast;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    MyApp mp;//全局变量
    String TAG="MainActivity";
    String province,districtname;
    Handler handler;

    //实时天气数据
    String temp , wind_direction , wind_strength , humidity , time;
    //today数据
    String temperature , weather ,wind , week ,city,date_y ,dressing_index,dressing_advice,
    travel_index,exercise_index;
    //未来天气
    String fu_week1,fu_temperature1,fu_weather1,fu_wind1,fu_data1,
    fu_week2,fu_temperature2,fu_weather2,fu_wind2,fu_data2,
            fu_week3,fu_temperature3,fu_weather3,fu_wind3,fu_data3,
    fu_week4,fu_temperature4,fu_weather4,fu_wind4,fu_data4,
    fu_week5,fu_temperature5,fu_weather5,fu_wind5,fu_data5,
            fu_week6,fu_temperature6,fu_weather6,fu_wind6,fu_data6;
    //天气id
    String fa,fa1,fa2,fa3,fa4,fa5,fa6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp=(MyApp) getApplication();
        districtname = mp.getNeed_data();
        province=mp.getProvince();//得到城市名


        //未来
        final TextView tv_fu1=findViewById(R.id.tv_fu1);
        final TextView tv_fu2=findViewById(R.id.tv_fu2);
        final TextView tv_fu3=findViewById(R.id.tv_fu3);
        final TextView tv_fu4=findViewById(R.id.tv_fu4);
        final TextView tv_fu5=findViewById(R.id.tv_fu5);
        //final TextView tv_fu6=findViewById(R.id.tv_fu6);


        final  ImageView tp1=findViewById(R.id.tp1);
        final  ImageView tp2=findViewById(R.id.tp2);
        final  ImageView tp3=findViewById(R.id.tp3);
        final  ImageView tp4=findViewById(R.id.tp4);
        final  ImageView tp5=findViewById(R.id.tp5);
        //final  ImageView tp6=findViewById(R.id.tp6);
        /////////////////////////////////////////////////////////
        final  TextView tv_province_city=findViewById(R.id.tv_province_city);
        final  TextView tv_time=findViewById(R.id.time);
        final  TextView tv_shidu=findViewById(R.id.shidu);
        final TextView tv_temp1=findViewById(R.id.nowtemp);
        final TextView tv_wind=findViewById(R.id.wind);
        final TextView tv_wind_direction=findViewById(R.id.wind_direction);
        final TextView tv_wind_strength=findViewById(R.id.wind_strength);
        final ImageView icon=findViewById(R.id.thumbnailIcon);
        final TextView tv_week=findViewById(R.id.tv_week);
        final TextView tv_temp2=findViewById(R.id.temp);
        final TextView tv_weather=findViewById(R.id.tv_weather);
        final TextView tv_dressing_index=findViewById(R.id.tv_dressing_index);





        //异步获取天气
        OkHttpClient client = new OkHttpClient();
        String url="http://v.juhe.cn/weather/index?format=2&cityname="+districtname+"&key=c3ff0828efe6c003b83c657919373e2b";
                //f2b970ea00aac9b67f82daaca95f25d6
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
                    throw new IOException("");
                }else {
                    String jsonStr = response.body().string();
                    if (jsonStr!=null){
                        try {
                            //解析json
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            Log.d("jsonstr-----", jsonStr);
                            JSONObject result = jsonObject.getJSONObject("result");
                            JSONObject sk = result.getJSONObject("sk");
                            JSONObject today = result.getJSONObject("today");
                            JSONObject  weather_id= today.getJSONObject("weather_id");
                            //获取天气id
                            fa= weather_id.getString("fa");
                            Log.d(TAG,"---------"+fa);




                            //未来
                            JSONArray future = result.getJSONArray("future");
                            JSONObject future01 = future.getJSONObject(1);
                            JSONObject future02 = future.getJSONObject(2);
                            JSONObject future03 = future.getJSONObject(3);
                            JSONObject future04 = future.getJSONObject(4);
                            JSONObject future05 = future.getJSONObject(5);
                            JSONObject future06 = future.getJSONObject(6);

                            //获取未来天气id
                            JSONObject  fu1_id= future01.getJSONObject("weather_id");
                            JSONObject  fu2_id= future02.getJSONObject("weather_id");
                            JSONObject  fu3_id= future03.getJSONObject("weather_id");
                            JSONObject  fu4_id= future04.getJSONObject("weather_id");
                            JSONObject  fu5_id= future05.getJSONObject("weather_id");
                            JSONObject  fu6_id= future06.getJSONObject("weather_id");
                            fa1= fu1_id.getString("fa");
                            fa2= fu2_id.getString("fa");
                            fa3= fu3_id.getString("fa");
                            fa4= fu4_id.getString("fa");
                            fa5= fu5_id.getString("fa");
                            fa6= fu6_id.getString("fa");
                            Log.d(TAG,"-------"+fa1);
                            Log.d(TAG,"-------"+fa2);
                            Log.d(TAG,"-------"+fa3);



                            //获得json内实时数据
                            temp=sk.getString("temp");
                            wind_direction = sk.getString("wind_direction");
                            wind_strength = sk.getString("wind_strength");
                            humidity = sk.getString("humidity");
                            time  = sk.getString("time");
                            //获取json内today数据
                            temperature = today.getString("temperature");
                            weather = today.getString("weather");
                            wind = today.getString("wind");
                            //-----------------------------
                            Log.d(TAG,"--------------"+wind);
                            //--------------------------
                            week = today.getString("week");
                            city = today.getString("city");
                            date_y = today.getString("date_y");
                            dressing_index = today.getString("dressing_index");
                            dressing_advice =today.getString("dressing_advice");
                            travel_index=today.getString("travel_index");
                            exercise_index=today.getString("exercise_index");
                            //未来天气
                            fu_week1=future01.getString("week");
                            fu_temperature1=future01.getString("temperature");
                            fu_weather1=future01.getString("weather");
                            fu_wind1=future01.getString("wind");

                            fu_week2=future02.getString("week");
                            fu_temperature2=future02.getString("temperature");
                            fu_weather2=future02.getString("weather");
                            fu_wind2=future02.getString("wind");
//
                            fu_week3=future03.getString("week");
                            fu_temperature3=future03.getString("temperature");
                            fu_weather3=future03.getString("weather");
                            fu_wind3=future03.getString("wind");
//
                            fu_week4=future04.getString("week");
                            fu_temperature4=future04.getString("temperature");
                            fu_weather4=future04.getString("weather");
                            fu_wind4=future04.getString("wind");

                            fu_week5=future05.getString("week");
                            fu_temperature5=future05.getString("temperature");
                            fu_weather5=future05.getString("weather");
                            fu_wind5=future05.getString("wind");
                            Log.d(TAG,"-----55555"+fu_wind5);

                            fu_week6=future06.getString("week");
                            fu_temperature6=future06.getString("temperature");
                            fu_weather6=future06.getString("weather");
                            fu_wind6=future06.getString("wind");
                            Log.d(TAG,"---------"+fu_week1);
                            Log.d(TAG,"---------"+fu_temperature1);
                            Log.d(TAG,"---------"+fu_weather1);
                            Log.d(TAG,"---------"+fu_wind1);





                            //通知主线程数据获取完毕
                            Message m = new Message();
                            m.what = 0x666;
                            handler.sendMessage(m);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }

            }
        });


        //接受信息获取完成
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                String name = "d"+fa;
                int imgId=getImgId(name);
                icon.setImageResource(imgId);
                //未来几天天气图标
                String name1 = "d"+fa1;
                int img1Id=getImgId(name1);
                tp1.setImageResource(img1Id);
                String name2 = "d"+fa2;
                int img2Id=getImgId(name2);
                tp2.setImageResource(img2Id);
                String name3 = "d"+fa3;
                int img3Id=getImgId(name3);
                tp3.setImageResource(img3Id);
                String name4 = "d"+fa4;
                int img4Id=getImgId(name4);
                tp4.setImageResource(img4Id);
                String name5 = "d"+fa5;
                int img5Id=getImgId(name5);
                tp5.setImageResource(img5Id);
                String name6 = "d"+fa6;
                int img6Id=getImgId(name6);
                //tp6.setImageResource(img6Id);



                if (msg.what==0x666){
                    tv_province_city.setText(province+" "+city);
                    tv_time.setText("更新时间："+time);
                    tv_shidu.setText("湿度:"+humidity);
                    tv_temp1.setText(temp+"°");
                    tv_wind.setText(wind);
                    tv_wind_direction.setText(wind_direction);
                    tv_wind_strength.setText(wind_strength);
                    tv_week.setText(date_y+week);
                    tv_temp2.setText(temperature);
                    tv_weather.setText(weather);
                    tv_dressing_index.setText(dressing_index);




                    //未来
//                    weilai.setText("【未来天气】"+"\n"+"-------------------------------------------------------");
                    tv_fu1.setText(fu_week1+"  "+fu_temperature1+"  "+fu_weather1+"  "+fu_wind1+" ");
                    tv_fu2.setText(fu_week2+"  "+fu_temperature2+"  "+fu_weather2+"  "+fu_wind2+" ");
                    tv_fu3.setText(fu_week3+"  "+fu_temperature3+"  "+fu_weather3+"  "+fu_wind3+" ");
                    tv_fu4.setText(fu_week4+"  "+fu_temperature4+"  "+fu_weather4+"  "+fu_wind4+" ");
                    tv_fu5.setText(fu_week5+"  "+fu_temperature5+"  "+fu_weather5+"  "+fu_wind5+" ");
                    //tv_fu6.setText(fu_week6+"  "+fu_temperature6+"  "+fu_weather6+"  "+fu_wind6+" ");
                    Log.d("test","---------------"+mp.getNeed_data());
                    //测试数据获取
                    Log.d(TAG,"实时数据"+temp+" "+wind_direction+" "+wind_strength+" "+humidity+" "+time);
                    Log.d(TAG,"=========================================================4444");
                    Log.d(TAG,"今日数据"+" "+temperature+" "+weather+" "+wind+" "+week+" "+date_y+" "+dressing_index);
                }
            }
        };




        //切换城市
        TextView tv_changcity = findViewById(R.id.tv_changcity);
        tv_changcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    public int getImgId(String name){
        int imgId=getResources().getIdentifier(name,"drawable",getPackageName());
        return imgId;
    }
}
