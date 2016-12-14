package com.ipads.miniguider;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.service.HttpGetData;
import com.service.HttpGetListener;

public class WeatherDemo extends Activity implements HttpGetListener {
    TextView mTextView;

    private String url =
            "http://api.map.baidu.com/telematics/v3/weather?"
            + "location=北京&"
            + "output=json&"
            + "ak=7cMyv5e9kBqzEb3MbNt6wR47IAbF0NXP&"
            + "mcode=B9:4A:FC:7B:AA:97:14:1C:7E:66:65:23:A0:CF:7D:95:D1:40:39:86;com.ipads.miniguider";

    private HttpGetData mhttpgetdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_demo);

        mhttpgetdata = (HttpGetData) new HttpGetData(url,this).execute();
        mTextView = (TextView) findViewById(R.id.weather_textView);


    }

    @Override
    public void GetDataUrl(String data) {
        // TODO Auto-generated method stub
        System.out.println(data);
        mTextView.setText(data);
    }
}
