package com.kiplening.demo.activity.Main;

import android.content.Context;

import com.kiplening.demo.common.MyApplication;
import com.kiplening.demo.module.App;
import com.kiplening.demo.tools.DataBaseUtil;

import java.util.ArrayList;

/**
 * Created by MOON on 9/15/2016.
 */
public class MainInteracterImpl implements MainInteracter {
    private Context context = MyApplication.getInstance();
    private DataBaseUtil dataBaseUtil = new DataBaseUtil(context);
    @Override
    public void login(String PWD, onLoginListener listener) {
        if (PWD == null)
            listener.onPasswordNull();
        else if (PWD.equals(dataBaseUtil.getPWD()))
            listener.onSuccess();
        else
            listener.onPasswordError();

    }

    @Override
    public String getStatus() {
         return dataBaseUtil.getStatus();
    }

    @Override
    public ArrayList<App> getAll(ArrayList<App> list) {
        list = dataBaseUtil.getAll();
        return list;
    }
}
