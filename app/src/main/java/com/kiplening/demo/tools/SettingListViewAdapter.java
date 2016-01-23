package com.kiplening.demo.tools;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kiplening.demo.R;

/**
 * Created by MOON on 1/23/2016.
 */
public class SettingListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private ListItemView listItemView;
    private String status;

    private SQLiteDatabase db;
    private String dataBaseName = "kiplening";
    private String tableName = "settings";
    private final DataBaseHelper helper;
    private DataBaseUtil dataBaseUtil = new DataBaseUtil();

    public SettingListViewAdapter(Context context,String status) {
        this.context = context;
        this.status = status;
        helper = new DataBaseHelper(context,dataBaseName,null,1,null);
        db = helper.getWritableDatabase();
        listContainer = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO 获取布局信息
        if (convertView == null){
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.setting_list_item, null);
            //获取控件对象
            listItemView.textView = (TextView)convertView.findViewById(R.id.item);
            listItemView.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);

            //设置控件集到convertView
            convertView.setTag(listItemView);
        }else {
            listItemView = (ListItemView)convertView.getTag();
        }

        listItemView.textView.setText("开启应用锁");
        if (status.equals("true")){
            listItemView.checkBox.setChecked(true);
        }
        else {
            listItemView.checkBox.setChecked(false);
        }

        listItemView.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    db.execSQL("delete from settings");
                    ContentValues cv = new ContentValues();
                    cv.put("status", "true");
                    db.insert("settings",null,cv);
                    Intent intent = new Intent("android.intent.action.SET_BROADCAST");
                    intent.putExtra("status", "true");
                    context.sendBroadcast(intent);
                    System.out.println(dataBaseUtil.getStatus(db));
                } else {
                    db.execSQL("delete from settings");
                    ContentValues cv = new ContentValues();
                    cv.put("status", "false");
                    db.insert("settings", null, cv);
                    Intent intent = new Intent("android.intent.action.SET_BROADCAST");
                    intent.putExtra("status", "false");
                    context.sendBroadcast(intent);
                }
            }


        });

        return convertView;
    }

    class ListItemView{
        TextView textView;
        CheckBox checkBox;
    }
}
