package com.kiplening.demo.activity.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kiplening.demo.R;
import com.kiplening.demo.common.MyApplication;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.androidlib.activity.BaseActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by MOON on 1/22/2016.
 */
public class SettingActivity extends BaseActivity{
    private String dataBaseName = "kiplening";

    @InjectView(R.id.checkBox) CheckBox isOpen;
    @InjectView(R.id.email) TextView email;
    @InjectView(R.id.pwd) TextView pwd;
    @InjectView(R.id.about) TextView about;
    //@InjectView(R.layout.setting_email) View inputEmail;
    private LayoutInflater layoutInflater;
    private DataBaseUtil dataBaseUtil;
    private ArrayList<String> lockList = MyApplication.getLockList();
    private String status;
    private Context context;


    //public final DataBaseHelper helper = new DataBaseHelper(this,dataBaseName,null,1,null);
    //private SettingListViewAdapter adapter;

    @Override
    protected void initVariables() {
        layoutInflater = LayoutInflater.from(this);
        context = this;

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        //final View inputEmail = layoutInflater.inflate(R.layout.setting_email, null);


        dataBaseUtil = new DataBaseUtil(MyApplication.getInstance());
        status = dataBaseUtil.getStatus();
        if (status.equals("true")){
            isOpen.setChecked(true);
        }
        else {
            isOpen.setChecked(false);
        }
        isOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dataBaseUtil.setStatus("true");
                    Intent intent = new Intent("android.intent.action.SET_BROADCAST");
                    //Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
                    intent.putStringArrayListExtra("lockList", lockList);
                    intent.putExtra("status", "false");
                    sendBroadcast(intent);
                }
                else {
                    dataBaseUtil.setStatus("false");
                    Intent intent = new Intent("android.intent.action.SET_BROADCAST");
                    //Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
                    intent.putStringArrayListExtra("lockList", lockList);
                    intent.putExtra("status", "false");
                    sendBroadcast(intent);
                }

            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        new AlertDialog.Builder(context).
                        setTitle("绑定邮箱").
                        setMessage("用于找回密码")
                        .setView(layoutInflater.inflate(R.layout.setting_email, null))
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //finish();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO 添加保存邮箱的操作
                                //finish();
                            }
                        }).show();

            }
        });
        pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this,SetPwdActivity.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "更多信息还未添加", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void loadData() {
        Intent intent = this.getIntent();


    }
}
