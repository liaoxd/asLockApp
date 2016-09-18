package com.kiplening.demo.activity.Home;

/**
 * Created by MOON on 9/18/2016.
 */
public interface HomeInteracter {
    interface onLoginListener{
        void onPasswordError();
        void onSuccess();
    }

    void login(String password, onLoginListener listener);
}
