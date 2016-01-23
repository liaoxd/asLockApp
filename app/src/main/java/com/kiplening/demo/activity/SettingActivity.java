package com.kiplening.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.kiplening.demo.R;
import com.kiplening.demo.tools.SettingListViewAdapter;

/**
 * Created by MOON on 1/22/2016.
 */
public class SettingActivity extends AppCompatActivity{
    private ListView myList;
    private SettingListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent = this.getIntent();
        String status = intent.getStringExtra("status");
        myList = (ListView)findViewById(R.id.setting_list);
        adapter = new SettingListViewAdapter(this,status);
        myList.setAdapter(adapter);
    }
}
