package com.kiplening.demo.activity;

import android.app.Activity;
import android.content.Context;
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
    private Context ctx;
    private Activity act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
