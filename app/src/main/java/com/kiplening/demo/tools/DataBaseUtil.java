package com.kiplening.demo.tools;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kiplening.demo.module.App;

import java.util.ArrayList;

/**
 * Created by MOON on 1/20/2016.
 */
public class DataBaseUtil {
    SQLiteDatabase db;

    //name of database
    private String dataBaseName = "kiplening";
    //table name
    private String tableNmae = "app";

    public ArrayList<App> getAll(SQLiteDatabase db){
        ArrayList<App> lockList = new ArrayList<App>();
        Cursor c = db.rawQuery("select * from "+tableNmae,null);
        if (c.moveToFirst()){ //判断游标是否为空
            App a = new App(c.getString(c.getColumnIndex("packageName")),c.getString(c.getColumnIndex("name")));
            lockList.add(a);
            for (int i = 0;i<c.getCount();i++){
                if (c.moveToNext()){
                    App app = new App(c.getString(c.getColumnIndex("packageName")),c.getString(c.getColumnIndex("name")));
                    lockList.add(app);
                }
            }
        }
        return lockList;
    }

    public long insert(SQLiteDatabase db,App app){
        ContentValues cv = new ContentValues();
        cv.put("packageName",app.getPackageName());
        cv.put("name",app.getName());
        Long result = db.insert(tableNmae, null, cv);
        return result;
    }

    public Long delete(SQLiteDatabase db,App app){
        String[] args = {String.valueOf(app.getPackageName())};
        long result = db.delete(tableNmae,"packageName=?",args);
        return result;
    }
}
