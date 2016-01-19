package com.kiplening.demo.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiplening.demo.R;

import java.util.List;
import java.util.Map;

/**
 * Created by MOON on 1/19/2016.
 */
public class ListViewAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater listContainer;
    private List<Map<String, Object>> listItems;
    private boolean[] hasChecked;
    ListItemView listItemView = null;

    public ListViewAdapter(Context context,List<Map<String,Object>> listItems) {
        this.context = context;

        //创建视图容器
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;
        hasChecked = new boolean[getCount()];
    }

    @Override
    public int getCount() {
        return listItems.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        //TODO 获取布局信息
        if (convertView == null){
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.list_item, null);
            //获取控件对象
            listItemView.icon = (ImageView)convertView.findViewById(R.id.icon);
            listItemView.name = (TextView)convertView.findViewById(R.id.name);
            listItemView.info = (TextView)convertView.findViewById(R.id.info);
            listItemView.button = (Button)convertView.findViewById(R.id.button);

            //设置控件集到convertView
            convertView.setTag(listItemView);
        }else {
            listItemView = (ListItemView)convertView.getTag();
        }
        listItemView.icon.setImageDrawable((Drawable)listItems.get(position).get("icon"));
        listItemView.name.setText((String) listItems.get(position).get("name"));
        listItemView.info.setText((String)listItems.get(position).get("info"));
        listItemView.button.setText((String)listItems.get(position).get("flag"));

        listItemView.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:锁上该应用
                if (listItems.get(position).get("flag").equals("已锁定")) {
                    listItems.get(position).put("flag", "锁定");
                    System.out.println("delete");
                }
                else {
                    listItems.get(position).put("flag", "已锁定");
                    System.out.println("lock");
                }
                //thread.start();
                notifyDataSetChanged();

            }
        });
        return convertView;
    }

    /**
     * 封装两个视图组件的类
     */
    class ListItemView {
        ImageView icon;
        TextView name;
        TextView info;
        Button button;
    }
}
