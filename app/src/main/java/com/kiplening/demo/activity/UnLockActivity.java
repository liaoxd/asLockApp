package com.kiplening.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.kiplening.demo.MainApplication;
import com.kiplening.demo.R;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.androidlib.activity.BaseActivity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MOON on 1/19/2016.
 */
public class UnLockActivity extends BaseActivity{
    private EditText edit;
    private Context ctx;
    private Activity act;
    private DataBaseUtil dataBaseUtil;
    private HashMap<String,Boolean> booleanState = MainApplication.getBooleanState();

    @Override
    protected void initVariables() {
        ctx = this;
        act = this;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_unlock);

        edit = (EditText)this.findViewById(R.id.edit);
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();

        Timer timer = new Timer(); //设置定时器
        timer.schedule(new TimerTask() {
            @Override
            public void run() { //弹出软键盘的代码
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edit, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }, 100); //设置300毫秒的时长
        dataBaseUtil = new DataBaseUtil(MainApplication.getInstance());
        edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Editable editable = edit.getText();
                int start = edit.getSelectionStart();
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //除了判断当前按键的 keyCode 以外，判定当前的动作。
                    //不然这个方法在 ACTION_DOWN 和ACTION_UP的时候都会被调用
                    //这样会导致多增加一个空的任务。需要多加注意。
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (edit.getText().toString().equals(dataBaseUtil.getPWD())){
                            //act.finish();

                            booleanState.put("isInputPWD",Boolean.TRUE);
                            //System.out.println(MainApplication.getIsInputPED()+" "+isInputPWD);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow((IBinder) edit,InputMethodManager.RESULT_HIDDEN);
                            act.finish();
                        }
                        else {
                            editable.delete(0, start);
                            Toast.makeText(act, "密码错误，请重输" + dataBaseUtil.getPWD(), Toast.LENGTH_SHORT).show();
                        }

                    }
                    return true;
                }
                return false;
            }
        });
        /*
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
        */
    }

    @Override
    protected void loadData() {

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
