package com.ipads.miniguider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
    }

    public void onClickRoute(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, RoutePlanDemo.class);
        startActivity(intent);
    }

    public void onBusQuery(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, BusLineDemo.class);
        startActivity(intent);
    }

    public void onWeatherQuery(View view) {
        Intent intent = new Intent();
        intent.putExtra("city", "上海");
        intent.setClass(MainActivity.this, SeeWeatherDemo.class);
        startActivity(intent);
    }

    public void onCollection(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, CollectDemo.class);
        startActivity(intent);
    }
}
