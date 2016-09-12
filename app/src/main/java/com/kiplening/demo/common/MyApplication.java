package com.kiplening.demo.common;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MOON on 3/2/2016.
 */
public class MyApplication extends Application {
    public static MyApplication instance;
    public static MyApplication getInstance(){return instance;}
    private static ArrayList<String> lockList;
    public static ArrayList<String> getLockList(){return lockList;}
    private static HashMap<String,Boolean> booleanStates;
    public static HashMap<String, Boolean> getBooleanState(){return booleanStates;}


    public MyApplication(){
        super.onCreate();
        this.instance = this;
        this.lockList = new ArrayList<>();
        this.booleanStates = new HashMap<>();
    }
}
