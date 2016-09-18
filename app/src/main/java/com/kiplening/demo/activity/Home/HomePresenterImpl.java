package com.kiplening.demo.activity.Home;

/**
 * Created by MOON on 9/18/2016.
 */
public class HomePresenterImpl implements HomePresenter,HomeInteracter.onLoginListener {
    private HomeView homeView;
    private HomeInteracter homeInteracter;

    public HomePresenterImpl(HomeView homeView) {
        this.homeView = homeView;
        homeInteracter = new HomeInteracterImpl();
    }


    @Override
    public void verifyPWD(String PWD) {
        homeInteracter.login(PWD,this);
    }

    @Override
    public void onDestory() {
        homeView = null;
    }

    @Override
    public void onPasswordError() {
        homeView.setPWDError();
    }

    @Override
    public void onSuccess() {
        homeView.navigationToMain();
    }
}
