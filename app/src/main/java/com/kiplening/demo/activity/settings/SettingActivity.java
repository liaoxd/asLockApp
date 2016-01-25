package com.kiplening.demo.activity.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kiplening.demo.R;
import com.kiplening.demo.tools.DataBaseHelper;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.demo.tools.SettingListViewAdapter;

/**
 * Created by MOON on 1/22/2016.
 */
public class SettingActivity extends AppCompatActivity{
    private String dataBaseName = "kiplening";

    private ListView myList;
    private TextView email,pwd,about;
    public final DataBaseHelper helper = new DataBaseHelper(this,dataBaseName,null,1,null);
    private SQLiteDatabase db;
    private SettingListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View inputEmail = layoutInflater.inflate(R.layout.setting_email, null);
        final Context context = this;
        db = helper.getWritableDatabase();
        final DataBaseUtil dataBaseUtil = new DataBaseUtil();


        Intent intent = this.getIntent();
        String status = intent.getStringExtra("status");

        myList = (ListView)findViewById(R.id.setting_list);
        adapter = new SettingListViewAdapter(this,status);
        myList.setAdapter(adapter);

        email = (TextView)findViewById(R.id.email);
        pwd = (TextView)findViewById(R.id.pwd);
        about = (TextView)findViewById(R.id.about);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context).
                        setTitle("绑定邮箱").
                        setMessage("用于找回密码")
                        .setView(inputEmail)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO 添加保存邮箱的操作
                                finish();
                            }
                        }).show();
            }
        });
        pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String pwd = dataBaseUtil.getPWD(db);
                Intent intent = new Intent(SettingActivity.this,SetPwdActivity.class);
                //pwdIntent.putExtra("password","hehe");
                startActivity(intent);
                //Toast.makeText(SettingActivity.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "更多信息还未添加", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
