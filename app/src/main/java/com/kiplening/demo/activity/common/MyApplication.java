package com.kiplening.demo.activity.common;

import android.app.Application;

/**
 * Created by MOON on 3/2/2016.
 */
public class MyApplication extends Application {
    public static MyApplication instance;
    public static MyApplication getInstance(){return instance;}
    public MyApplication(){
        super.onCreate();
        this.instance = this;
    }
}
