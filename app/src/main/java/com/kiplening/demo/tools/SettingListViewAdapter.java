package com.kiplening.demo.tools;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kiplening.demo.R;

import java.util.ArrayList;

/**
 * Created by MOON on 1/23/2016.
 */
public class SettingListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private ListItemView listItemView;
    private String status;
    private ArrayList<String> lockList;
    private DataBaseUtil dataBaseUtil;

    public SettingListViewAdapter(Context context,String status) {
        this.context = context;
        this.status = status;
        dataBaseUtil = new DataBaseUtil(context);

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

                lockList = dataBaseUtil.getAllLocked();
                if (isChecked) {

                    dataBaseUtil.setStatus("true");
                    Intent intent = new Intent("android.intent.action.SET_BROADCAST");
                    intent.putStringArrayListExtra("lockList", lockList);
                    intent.putExtra("status", "true");
                    context.sendBroadcast(intent);
                    System.out.println(dataBaseUtil.getStatus());
                } else {
                    dataBaseUtil.setStatus("false");
                    Intent intent = new Intent("android.intent.action.SET_BROADCAST");
                    intent.putStringArrayListExtra("lockList", lockList);
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
    class ListItemView2{
        TextView pwd;
    }
}
