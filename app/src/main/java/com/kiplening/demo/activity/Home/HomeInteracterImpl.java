package com.kiplening.demo.activity.Home;

import com.kiplening.demo.MainApplication;
import com.kiplening.demo.tools.DataBaseUtil;

/**
 * Created by MOON on 9/18/2016.
 */
public class HomeInteracterImpl implements HomeInteracter {
    private DataBaseUtil dataBaseUtil = new DataBaseUtil(MainApplication.getInstance());
    @Override
    public void login(String password, onLoginListener listener) {
        if (dataBaseUtil.getPWD().equals(password))
            listener.onSuccess();
        else
            listener.onPasswordError();
    }
}
