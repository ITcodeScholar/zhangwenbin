package com.example.xyxy001.weatherforecast;

import android.app.Application;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xyxy001 on 2018/1/5.
 */

public class MyApp extends Application {

    List<String> row_data;
    HashMap<String, List<String>> district_data ;
    HashMap<String, List<String>> city_data ;
    String need_data="新余";//城市初始值为新余
    String province="江西";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List getRow_data(){
        return (this.row_data);
    }
    public void setRow_data(List row_data){
        this.row_data=row_data;
    }

    public HashMap getDistrict_data(){
        return (this.district_data);
    }
    public void setDistrict_data(HashMap district_data){
        this.district_data=district_data;
    }

    public HashMap getCity_data(){
        return (this.city_data);
    }
    public void setCity_data(HashMap city_data){
        this.city_data=city_data;
    }

    public String getNeed_data(){
        return this.need_data;
    }
    public void setNeed_data(String need_data){
        this.need_data=need_data;
    }

    public String getProvince(){
        return (this.province);
    }
    public void setProvince(String province){
        this.province=province;
    }
}
