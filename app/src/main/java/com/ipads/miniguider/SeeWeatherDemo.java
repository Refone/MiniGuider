package com.ipads.miniguider;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.service.HttpGetData;
import com.service.HttpGetListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.baidu.location.b.g.p;


public class SeeWeatherDemo extends AppCompatActivity implements HttpGetListener {

    private HttpGetData mhttpgetdata;
    private String city = null;

    private TextView tView;
    private Toolbar toolbar;
    boolean needfresh = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_weather_demo);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(city);


        tView = (TextView) findViewById(R.id.test);
        tView.setVisibility(View.GONE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tView.getVisibility()==View.GONE) {
                    tView.setVisibility(View.VISIBLE);
                } else {
                    ChangeCity();
                }
            }
        });

        mhttpgetdata = (HttpGetData) new HttpGetData(UrlCreator(city),this).execute();
    }

    private void ChangeCity() {
        finish();
        Intent intent = new Intent();
        intent.putExtra("city", tView.getText().toString());
        intent.setClass(SeeWeatherDemo.this, SeeWeatherDemo.class);
        startActivity(intent);
    }

    private String UrlCreator(String c) {
        String url =
                "http://api.map.baidu.com/telematics/v3/weather?"
                + "location="+ c +"&"
                + "output=json&"
                + "ak=7cMyv5e9kBqzEb3MbNt6wR47IAbF0NXP&"
                + "mcode=B9:4A:FC:7B:AA:97:14:1C:7E:66:65:23:A0:CF:7D:95:D1:40:39:86;com.ipads.miniguider";

        return url;
    }

    @Override
    public void GetDataUrl(String data) {
        System.out.println(data);

        JSONObject jo = null;
        JSONArray ja = null;
        View v = null;
        TextView t = null;
        ImageView img = null;

        try {
            jo = new JSONObject(data);
            ja = jo.getJSONArray("results");

            if (jo.getString("error")!= "0"){
                return;
            }
            //today
            {
                JSONObject jtoday = ja.getJSONObject(0).getJSONArray("weather_data").getJSONObject(0);

                t = (TextView) findViewById(R.id.temp_flu);
                String pattern = "([-]*)(\\d+)(℃)";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(jtoday.getString("date").toString());
                if (m.find()) {
                    t.setText(m.group(0));
                } else {
                    t.setText("nop");
                }

                pattern = "([-]*)(\\d+)(\\D+)([-]*)(\\d+)(℃)";
                r = Pattern.compile(pattern);
                m = r.matcher(jtoday.getString("temperature").toString());
                if (m.find()) {
                    t = (TextView)findViewById(R.id.temp_max);
                    t.setText(" 最高："+m.group(1)+m.group(2)+"℃");
                    t = (TextView)findViewById(R.id.temp_min);
                    t.setText(" 最低："+m.group(4)+m.group(5)+m.group(6));
                }

                String pm25 = ja.getJSONObject(0).getString("pm25");
                t = (TextView)findViewById(R.id.temp_pm);
                t.setText("PM 2.5:"+pm25+"μg/m³ ");

                t = (TextView)findViewById(R.id.temp_quality);
                t.setText(airQuantity(ja.getJSONObject(0).getString("pm25")));

                img = (ImageView)findViewById(R.id.weather_icon);
                String url = getPng(jtoday.getString("weather"));
                int ImageSource = getResources().getIdentifier(url, null, getPackageName());
                Drawable image = getResources().getDrawable(ImageSource);
                img.setImageDrawable(image);
            }

            //suggestion
            {
                JSONObject jsg = ja.getJSONObject(0).getJSONArray("index").getJSONObject(0);
                t = (TextView)findViewById(R.id.cloth_brief);
                t.setText("穿衣指数---"+jsg.getString("zs"));
                t = (TextView)findViewById(R.id.cloth_txt);
                t.setText(jsg.getString("des"));

                jsg = ja.getJSONObject(0).getJSONArray("index").getJSONObject(4);
                t = (TextView)findViewById(R.id.sport_brief);
                t.setText("运动指数---"+jsg.getString("zs"));
                t = (TextView)findViewById(R.id.sport_txt);
                t.setText(jsg.getString("des"));

                jsg = ja.getJSONObject(0).getJSONArray("index").getJSONObject(2);
                t = (TextView)findViewById(R.id.travel_brief);
                t.setText("旅游指数---"+jsg.getString("zs"));
                t = (TextView)findViewById(R.id.travel_txt);
                t.setText(jsg.getString("des"));

                jsg = ja.getJSONObject(0).getJSONArray("index").getJSONObject(3);
                t = (TextView)findViewById(R.id.flu_brief);
                t.setText("感冒指数---"+jsg.getString("zs"));
                t = (TextView)findViewById(R.id.flu_txt);
                t.setText(jsg.getString("des"));
            }

            //forecast
            {
                JSONObject jd = ja.getJSONObject(0).getJSONArray("weather_data").getJSONObject(0);
                v = (View)findViewById(R.id.forecast_day0);
                img = (ImageView)v.findViewById(R.id.forecast_icon);
                String url = getPng(jd.getString("weather"));
                int ImageSource = getResources().getIdentifier(url, null, getPackageName());
                Drawable image = getResources().getDrawable(ImageSource);
                img.setImageDrawable(image);
                t = (TextView)v.findViewById(R.id.forecast_date);
                t.setText("今天");
                t = (TextView)v.findViewById(R.id.forecast_temp);
                t.setText(jd.getString("temperature"));
                t = (TextView)v.findViewById(R.id.forecast_txt);
                t.setText("天气："+jd.getString("weather")+"  风力："+jd.getString("wind"));

                jd = ja.getJSONObject(0).getJSONArray("weather_data").getJSONObject(1);
                v = (View)findViewById(R.id.forecast_day1);
                img = (ImageView)v.findViewById(R.id.forecast_icon);
                url = getPng(jd.getString("weather"));
                ImageSource = getResources().getIdentifier(url, null, getPackageName());
                image = getResources().getDrawable(ImageSource);
                img.setImageDrawable(image);
                t = (TextView)v.findViewById(R.id.forecast_date);
                t.setText(jd.getString("date"));
                t = (TextView)v.findViewById(R.id.forecast_temp);
                t.setText(jd.getString("temperature"));
                t = (TextView)v.findViewById(R.id.forecast_txt);
                t.setText("天气："+jd.getString("weather")+"  风力："+jd.getString("wind"));

                jd = ja.getJSONObject(0).getJSONArray("weather_data").getJSONObject(2);
                v = (View)findViewById(R.id.forecast_day2);
                img = (ImageView)v.findViewById(R.id.forecast_icon);
                url = getPng(jd.getString("weather"));
                ImageSource = getResources().getIdentifier(url, null, getPackageName());
                image = getResources().getDrawable(ImageSource);
                img.setImageDrawable(image);
                t = (TextView)v.findViewById(R.id.forecast_date);
                t.setText(jd.getString("date"));
                t = (TextView)v.findViewById(R.id.forecast_temp);
                t.setText(jd.getString("temperature"));
                t = (TextView)v.findViewById(R.id.forecast_txt);
                t.setText("天气："+jd.getString("weather")+"  风力："+jd.getString("wind"));

                jd = ja.getJSONObject(0).getJSONArray("weather_data").getJSONObject(3);
                v = (View)findViewById(R.id.forecast_day3);
                img = (ImageView)v.findViewById(R.id.forecast_icon);
                url = getPng(jd.getString("weather"));
                ImageSource = getResources().getIdentifier(url, null, getPackageName());
                image = getResources().getDrawable(ImageSource);
                img.setImageDrawable(image);
                t = (TextView)v.findViewById(R.id.forecast_date);
                t.setText(jd.getString("date"));
                t = (TextView)v.findViewById(R.id.forecast_temp);
                t.setText(jd.getString("temperature"));
                t = (TextView)v.findViewById(R.id.forecast_txt);
                t.setText("天气："+jd.getString("weather")+"  风力："+jd.getString("wind"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "输入信息有误", Toast.LENGTH_SHORT).show();
        }

    }

    private String airQuantity(String pm) {
        int p = Integer.parseInt(pm);
        String ret = "空气质量:";

        if (p<=50) {
            ret += "优";
        } else if (p<=100) {
            ret += "良";
        } else if (p<=150) {
            ret += "轻度污染";
        } else if (p<=200) {
            ret += "中度污染";
        } else if (p<=300) {
            ret += "重度污染";
        } else {
            ret += "不适合人类居住";
        }

        return ret;
    }

    private String getPng(String w) {
        String ret = "@drawable/sunny";

        if (w.indexOf("多云")!=-1) {
            ret = "@drawable/fog";
        } else if(w.indexOf("晴")!=-1){
            ret = "@drawable/sunny";
        } else if(w.indexOf("雷")!=-1) {
            ret = "@drawable/thunderstorm";
        } else if(w.indexOf("雨")!=-1) {
            ret = "@drawable/heavy_rain";
        } else if(w.indexOf("雪")!=-1) {
            ret = "@drawable/snow";
        } else {
            ret = "@drawable/cloudy";
        }

        return ret;
    }
}
