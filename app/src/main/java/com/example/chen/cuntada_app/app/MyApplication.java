package com.example.chen.cuntada_app.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Tokyo", "@@@@@@@@@@@@@@@@@@ MyApplication");
        context = getApplicationContext();
    }
}
