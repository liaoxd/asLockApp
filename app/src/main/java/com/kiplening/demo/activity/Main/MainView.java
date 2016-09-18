package com.kiplening.demo.activity.Main;

import com.kiplening.demo.module.App;

import java.util.ArrayList;

/**
 * Created by MOON on 9/14/2016.
 */
public interface MainView {
    void RequestPromission();
    void showList(ArrayList<App> list, String status);
    void onDestory();
    void navigationToSetting();
}
