package com.kiplening.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.kiplening.demo.R;
import com.kiplening.demo.tools.KeyboardUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by MOON on 1/19/2016.
 */
public class UnLockActivity extends AppCompatActivity{
    private EditText edit;
    private String packageName;
    private String passWord = "123456";
    private SharedPreferences preferences;
    private Context ctx;
    private Activity act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_unlock);
        ctx = this;
        act = this;

        edit = (EditText)this.findViewById(R.id.edit);
        edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                int currentVersion = android.os.Build.VERSION.SDK_INT;
                String methodName = null;
                if (currentVersion >= 16) {
                    // 4.2
                    methodName = "setShowSoftInputOnFocus";
                } else if (currentVersion >= 14) {
                    // 4.0
                    methodName = "setSoftInputShownOnFocus";
                }

                if (methodName == null) {
                    edit.setInputType(InputType.TYPE_NULL);
                } else {
                    Class<EditText> cls = EditText.class;
                    Method setShowSoftInputOnFocus;
                    try {
                        setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                        setShowSoftInputOnFocus.setAccessible(true);
                        setShowSoftInputOnFocus.invoke(edit, false);
                    } catch (NoSuchMethodException e) {
                        edit.setInputType(InputType.TYPE_NULL);
                        //e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        //e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        //e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        //e.printStackTrace();
                    }
                }
                int inputback = edit.getInputType();
                //edit.setInputType(InputType.TYPE_NULL);
                try {
                    KeyboardUtil ku = new KeyboardUtil(act, ctx, edit);
                    ku.showKeyboard();
                }catch (Exception e){
                    e.printStackTrace();
                }

                edit.setInputType(inputback);
                return false;
            }
        });
    }
/*
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
 */
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
    /*
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
    */
}
