package com.kiplening.demo.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kiplening.demo.module.App;

import java.util.ArrayList;

/**
 * Created by MOON on 1/20/2016.
 */
public class DataBaseUtil {
    private SQLiteDatabase db;

    //name of database
    private String dataBaseName = "kiplening";
    //table name
    private String tableName = "app";
    private Context context;

    public DataBaseUtil(Context context) {
        this.context = context;
        DataBaseHelper helper = new DataBaseHelper(context,dataBaseName,null,1,null);
        db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from settings",null);
        if (c != null){
            if (c.moveToFirst()){
                if (c.getString(c.getColumnIndex("id")) != null){
                    if (c.getString(c.getColumnIndexOrThrow("id")).equals("host")){
                        ContentValues cv = new ContentValues();
                        cv.put("id","host");
                        cv.put("password","null");
                        cv.put("status","true");
                        cv.put("email","null");
                        db.insert("settings",null,cv);
                    }
                }

            }
        }
    }

    public ArrayList<App> getAll(){
        ArrayList<App> lockList = new ArrayList<App>();
        Cursor c = db.rawQuery("select * from "+ tableName,null);
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
    public ArrayList<String> getAllLocked(){
        ArrayList<App> allApp = this.getAll();
        ArrayList<String> result = new ArrayList<>();
        for (App a:
             allApp) {
            result.add(a.getPackageName());
        }
        return result;
    }

    public long insert(App app){
        ContentValues cv = new ContentValues();
        cv.put("packageName",app.getPackageName());
        cv.put("name",app.getName());
        Long result = db.insert(tableName, null, cv);
        return result;
    }

    public Long delete(App app){
        String[] args = {String.valueOf(app.getPackageName())};
        long result = db.delete(tableName, "packageName=?", args);
        return result;
    }
    public String getStatus(){
        String result;
        Cursor c = db.rawQuery("select * from settings", null);
        if (c.moveToFirst()) { //判断游标是否为空
            result = c.getString(c.getColumnIndex("status"));
            return result;
        }else {
            ContentValues cv = new ContentValues();
            cv.put("status","true");
            db.insert("settings",null,cv);
            return "true";
        }

    }
    public void setStatus(String status){
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        db.insert("settings",null,cv);
        //修改条件
        String whereClause = "id=?";
        //修改添加参数
        String[] whereArgs= new String[]{String.valueOf("host")};
        //修改
        db.update("settings", cv, whereClause, whereArgs);

    }
    public String getPWD(){
        String result;
        Cursor c = db.rawQuery("select * from settings", null);
        if (c.moveToFirst()) { //判断游标是否为空
            //System.out.println(c.getString(c.getColumnIndex("id")));
            result = c.getString(c.getColumnIndex("password"));
            if (result != null){
                return result;
            }
            return "null";

        }else {
            ContentValues cv = new ContentValues();
            cv.put("password","123456");
            db.insert("settings",null,cv);
            return "123456";
        }
    }
    public boolean setPWD(String password){
        //实例化内容值
        //ContentValues values = new ContentValues();
        //在values中添加内容
        //values.put("password",password);
        //修改条件
        //String whereClause = "id=?";
        //修改添加参数
        //String[] whereArgs= new String[]{String.valueOf("host")};
        //修改
        //db.update("settings",values,whereClause,whereArgs);
        //db.execSQL("UPDATE settings SET password = "+password+" WHERE id = host");
        ContentValues cv = new ContentValues();
        cv.put("id", "host");
        cv.put("password", password);

        String[] args = {String.valueOf("host")};
        db.update("settings", cv, "id=?",args);
        return true;
    }
    public String getEmail(){
        String result;
        Cursor c = db.rawQuery("select * from settings", null);
        if (c.moveToFirst()) { //判断游标是否为空
            result = c.getString(c.getColumnIndex("email"));
            return result;
        }else {
            ContentValues cv = new ContentValues();
            cv.put("email","null");
            db.insert("settings",null,cv);
            return "null";
        }
    }
}
