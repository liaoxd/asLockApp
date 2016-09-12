package com.kiplening.demo.activity.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kiplening.demo.R;
import com.kiplening.demo.common.MyApplication;
import com.kiplening.demo.tools.DataBaseHelper;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.mylibrary.activity.BaseActivity;

/**
 * Created by MOON on 1/25/2016.
 */
public class SetPwdActivity extends BaseActivity {
    private String dataBaseName = "kiplening";

    private Context context;
    private Button buttonOK,buttonCancel;
    private TextView wrongMSG;
    private EditText inputPWD,inputPWDAgain;
    private DataBaseUtil dataBaseUtil;
    private DataBaseHelper helper = new DataBaseHelper(this,dataBaseName,null,1,null);


    @Override
    protected void initVariables() {
        context = this;
        dataBaseUtil = new DataBaseUtil(MyApplication.getInstance());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_pwd);
        buttonOK = (Button)findViewById(R.id.set_pwd_ok);
        buttonCancel = (Button)findViewById(R.id.set_pwd_cancel);
        inputPWD = (EditText)findViewById(R.id.input_pwd);
        inputPWDAgain = (EditText)findViewById(R.id.input_pwdAgain);
        wrongMSG = (TextView)findViewById(R.id.wrong_pwd);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputPWD.getText().toString().equals(inputPWDAgain.getText().toString())){
                    //inputPWD.getText()
                    String password = inputPWD.getText().toString();
                    if (dataBaseUtil.setPWD(password)) {
                        System.out.println("更改成功");
                        new AlertDialog.Builder(context)
                                .setMessage("更改密码成功")
                                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                    else{
                        new AlertDialog.Builder(context)
                                .setMessage("更改密码失败，请稍后再试。")
                                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
                else {
                    wrongMSG.setText("两次输入的密码不一样，请重输！");
                    inputPWDAgain.getText().delete(0,inputPWDAgain.getText().length());
                    inputPWD.getText().delete(0,inputPWD.getText().length());

                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
