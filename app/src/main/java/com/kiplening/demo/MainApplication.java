package com.kiplening.demo;

import android.app.Application;

import com.kiplening.demo.module.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MOON on 3/2/2016.
 */
public class MainApplication extends Application{

    public static MainApplication instance;
    public static MainApplication getInstance(){return instance;}

    private static Settings settings;
    public static Settings getSettings(){return settings;}

    private static ArrayList<String> lockList;
    public static ArrayList<String> getLockList(){return lockList;}

    private static HashMap<String,Boolean> booleanStates;
    public static HashMap<String, Boolean> getBooleanState(){return booleanStates;}

    @Override
    public void onCreate() {
        super.onCreate();
        //SoLoader.init(this,false);
    }

    public MainApplication(){
        super.onCreate();
        this.instance = this;
        this.lockList = new ArrayList<>();
        this.booleanStates = new HashMap<>();

        //SharedPreferences mSharedPreferences = this.getSharedPreferences("settings",MODE_PRIVATE);
    }

//    private static final TransMissionPackage mTransMissionPackage = new TransMissionPackage();
//
//    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
//
//
//        @Override
//        public boolean getUseDeveloperSupport() {
//            return BuildConfig.DEBUG;
//        }
//
//        @Override
//        protected List<ReactPackage> getPackages() {
//            return Arrays.<ReactPackage>asList(
//                    new MainReactPackage(),
//                    mTransMissionPackage
//            );
//        }
//    };

//    @Override
//    public ReactNativeHost getReactNativeHost() {
//        return mReactNativeHost;
//    }

    /**
     *包名
     */
    public String getAppPackageName() {
        return this.getPackageName();
    }

    /**
     * 获取 reactPackage
     * @return
     */
//    public static TransMissionPackage getReactPackage() {
//        return mTransMissionPackage;
//    }
//
//    protected List<ReactPackage> getPackages() {
//        return Arrays.<ReactPackage>asList(
//                new MainReactPackage(),
//                new TransMissionPackage()); // <-- 添加这一行，类名替换成你的Package类的名字.
//    }

}
