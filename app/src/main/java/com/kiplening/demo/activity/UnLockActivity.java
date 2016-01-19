package com.kiplening.demo.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.kiplening.demo.R;
import com.kiplening.demo.tools.KeyboardUtil;

/**
 * Created by MOON on 1/19/2016.
 */
public class UnLockActivity extends Activity implements View.OnClickListener,View.OnTouchListener{
    private EditText inputPwd;
    private String packageName;
    private String passWord;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_unlock);

        inputPwd = (EditText)this.findViewById(R.id.lock_pwd);
        inputPwd.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        new KeyboardUtil(this, this, inputPwd).showKeyboard();
        String input = inputPwd.getText().toString().trim();
        preferences = getSharedPreferences("passWord", MODE_PRIVATE);
        passWord = preferences.getString("pwd", "");
        if(TextUtils.isEmpty(input))
        {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }
        else if(passWord.equals(input))
        {
            //WatchAppService.lastRunningApp = WatchAppService.runningApp;//这里赋值，终于解决了反复弹出验证页面的BUG
            finish();
        }
        else
        {
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            inputPwd.setText("");//置空
        }
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
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        //这样是在触摸到控件时，软键盘才会显示出来
        int inputback = inputPwd.getInputType();
        inputPwd.setInputType(InputType.TYPE_NULL);
        new KeyboardUtil(this, this, inputPwd).showKeyboard();
        inputPwd.setInputType(inputback);
        return false;
    }
}
