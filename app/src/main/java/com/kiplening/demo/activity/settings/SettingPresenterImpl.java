package com.kiplening.demo.activity.settings;

/**
 * Created by MOON on 9/18/2016.
 */
public class SettingPresenterImpl implements SettingPresenter{
    private SettingView settingView;
    private SettingInteracter settingInteracter;

    public SettingPresenterImpl(SettingView settingView) {
        this.settingView = settingView;
        settingInteracter = new SettingInteracterImpl();
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void reSetPWD() {

    }

    @Override
    public void setEmail() {

    }

    @Override
    public void aboutUs() {

    }
}
