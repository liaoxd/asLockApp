package com.kiplening.demo.tools;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.kiplening.demo.R;

/**
 * Created by MOON on 1/19/2016.
 */
public class KeyboardUtil {
    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private EditText editText;
    public KeyboardUtil(Activity activity, Context context, EditText editText) {
        this.editText = editText;
        keyboard = new Keyboard(context, R.xml.symbols);
        keyboardView = (KeyboardView)activity.findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setVisibility(View.VISIBLE);
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
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_DELETE) {
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else {
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
}
