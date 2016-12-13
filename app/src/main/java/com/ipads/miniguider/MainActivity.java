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
}
