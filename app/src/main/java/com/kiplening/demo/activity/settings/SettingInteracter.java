package com.kiplening.demo.activity.settings;

/**
 * Created by MOON on 9/18/2016.
 */
public interface SettingInteracter {
    interface onResetPWDListener{
        void onSuccess();
        void onError();
    }
    void verifyOldPWD(String password);
}
