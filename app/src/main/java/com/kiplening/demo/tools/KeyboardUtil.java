package com.kiplening.demo.tools;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kiplening.demo.R;


/**
 * Created by MOON on 1/19/2016.
 */
public class KeyboardUtil {
    private Context ctx;
    private Activity act;
    private KeyboardView keyboardView;
    private Keyboard k;
    private EditText ed;


    private String password ;
    final DataBaseUtil dataBaseUtil;

    public KeyboardUtil(Activity act, Context ctx, EditText editText) {
        this.act = act;
        this.ctx = ctx;
        this.ed = editText;
        DataBaseHelper helper = new DataBaseHelper(act,"kiplening",null,1,null);
        dataBaseUtil = new DataBaseUtil(ctx);

        try {
            k = new Keyboard(ctx, R.xml.symbol);
        }catch (Exception e){

            e.printStackTrace();
        }

        keyboardView = (KeyboardView)act.findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(k);
        keyboardView.setEnabled(true);
        //keyboardView.setPreviewEnabled(true);
        //keyboardView.setVisibility(View.VISIBLE);
        keyboardView.setOnKeyboardActionListener(listener);
    }
    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            password = dataBaseUtil.getPWD();
            if (primaryCode == -5) {// 完成
                if (ed.getText().toString().equals(password)){
                    //act.finish();
                    act.finish();
                }
                else {
                    editable.delete(0, start);
                    Toast.makeText(act,"密码错误，请重输"+password,Toast.LENGTH_SHORT).show();
                }

                hideKeyboard();
            } else if (primaryCode == -2) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            }  else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }

    };
    public void showKeyboard(){
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }
}
