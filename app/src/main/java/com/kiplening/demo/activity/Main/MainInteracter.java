package com.kiplening.demo.activity.Main;

import com.kiplening.demo.module.App;

import java.util.ArrayList;

/**
 * Created by MOON on 9/14/2016.
 */
public interface MainInteracter {
    interface onLoginListener{
        void onPasswordNull();
        void onPasswordError();
        void onSuccess();
    }
    void login(String PWD,onLoginListener listener);

    String getStatus();

    void getAll(ArrayList<App> list);
}
