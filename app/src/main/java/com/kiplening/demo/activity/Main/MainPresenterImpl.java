package com.kiplening.demo.activity.Main;

import android.annotation.TargetApi;
import android.app.usage.UsageStatsManager;
import android.os.Build;

import com.kiplening.demo.common.MyApplication;
import com.kiplening.demo.module.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOON on 9/14/2016.
 */
public class MainPresenterImpl implements MainPresenter,MainInteracter.onLoginListener{
    private MainView mainView;
    private MainInteracter mainInteracter;
    private ArrayList<App> appList = new ArrayList<>();
    String status = mainInteracter.getStatus();

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        mainInteracter = new MainInteracterImpl();
    }

    @Override
    public void checkPromission() {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        while (currentVersion > 20) {
            if (!isNoSwitch()) {
                mainView.RequestPromission();

            } else {
                mainInteracter.getAll(appList);

                mainView.showList(appList,status);
                break;
            }
        }
        mainInteracter.getAll(appList);
        mainView.showList(appList,status);

    }

    @Override
    public void verifyPWD() {

    }

    @Override
    public void onDestory() {
        mainView = null;
    }

    @Override
    public void onPasswordNull() {

    }

    @Override
    public void onPasswordError() {

    }

    @Override
    public void onSuccess() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean isNoSwitch() {
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) MyApplication.getInstance()
                .getSystemService("usagestats");
        List queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        return !(queryUsageStats == null || queryUsageStats.isEmpty());
    }
}
