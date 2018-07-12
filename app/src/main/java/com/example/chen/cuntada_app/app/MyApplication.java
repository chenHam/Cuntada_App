package com.example.chen.cuntada_app.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MyApplication extends Application {
    public static Context context;
    public static SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
    }
}
