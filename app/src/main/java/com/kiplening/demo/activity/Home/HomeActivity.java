package com.kiplening.demo.activity.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.kiplening.androidlib.activity.BaseActivity;
import com.kiplening.demo.R;
import com.kiplening.demo.activity.Main.MainActivity;
import com.kiplening.demo.MainApplication;
import com.kiplening.demo.tools.DataBaseHelper;
import com.kiplening.demo.tools.DataBaseUtil;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by MOON on 4/17/2016.
 */
public class HomeActivity extends BaseActivity implements HomeView {

    private Activity act;
    private HomePresenter presenter;
    private String password ;
    @InjectView(R.id.edit) EditText edit;

    @Override
    protected void initVariables() {
        act = this;

        presenter = new HomePresenterImpl(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_unlock);
        ButterKnife.inject(this);
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();

        Toast.makeText(this,String.format(Locale.US,"http://%s/status","localhost:8081"),Toast.LENGTH_LONG);
        //Log.e(TAG, "initViews:"+String.format(Locale.US,"http://%s/status","localhost:8081"),null);
        Timer timer = new Timer(); //设置定时器
        timer.schedule(new TimerTask() {
            @Override
            public void run() { //弹出软键盘的代码
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edit, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }, 300); //设置300毫秒的时长

        edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int start = edit.getSelectionStart();
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //除了判断当前按键的 keyCode 以外，判定当前的动作。
                    //不然这个方法在 ACTION_DOWN 和ACTION_UP的时候都会被调用
                    //这样会导致多增加一个空的任务。需要多加注意。
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        presenter.verifyPWD(edit.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void loadData() {
        final DataBaseUtil dataBaseUtil;
        DataBaseHelper helper = new DataBaseHelper(act,"kiplening",null,1,null);
        dataBaseUtil = new DataBaseUtil(MainApplication.getInstance());
        password = dataBaseUtil.getPWD();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        //屏蔽后退键
        if(KeyEvent.KEYCODE_BACK == event.getKeyCode())
        {
            return true;//阻止事件继续向下分发
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void navigationToMain() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setPWDError() {
        Editable editable = edit.getText();
        int start = edit.getSelectionStart();
        editable.delete(0, start);
    }
}
