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

    public DataBaseUtil(Context context) {
        DataBaseHelper helper = new DataBaseHelper(context, dataBaseName, null, 1, null);
        db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from settings", null);
        if (c != null) {
            if (c.moveToFirst()) {
                if (c.getString(c.getColumnIndex("id")) != null) {
                    if (c.getString(c.getColumnIndexOrThrow("id")).equals("host")) {
                        ContentValues cv = new ContentValues();
                        cv.put("id", "host");
                        cv.put("password", "null");
                        cv.put("status", "true");
                        cv.put("email", "null");
                        db.insert("settings", null, cv);
                    }
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<App> getAll() {
        ArrayList<App> lockList = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + tableName, null);
        if (c.moveToFirst()) { //判断游标是否为空
            App a = new App(c.getString(c.getColumnIndex("packageName")), c.getString(c.getColumnIndex("name")));
            lockList.add(a);
            for (int i = 0; i < c.getCount(); i++) {
                if (c.moveToNext()) {
                    App app = new App(c.getString(c.getColumnIndex("packageName")), c.getString(c.getColumnIndex("name")));
                    lockList.add(app);
                }
            }
        }
        return lockList;
    }

    /**
     *  获取数据库当中所有锁定了的应用
     * @return 返回一个ArrayList<String>的数据
     */
    public ArrayList<String> getAllLocked() {
        ArrayList<App> allApp = this.getAll();
        ArrayList<String> result = new ArrayList<>();
        for (App a :
                allApp) {
            result.add(a.getPackageName());
        }
        return result;
    }

    public long insert(App app) {
        ContentValues cv = new ContentValues();
        cv.put("packageName", app.getPackageName());
        cv.put("name", app.getName());
        Long result = db.insert(tableName, null, cv);
        return result;
    }

    public Long delete(App app) {
        String[] args = {String.valueOf(app.getPackageName())};
        long result = db.delete(tableName, "packageName=?", args);
        return result;
    }

    /**
     *  获取当前应用锁的工作状态，为true表示应用锁开启，否则表示应用锁关闭
     * @return
     */
    public String getStatus() {
        String result;
        Cursor c = db.rawQuery("select * from settings", null);
        if (c.moveToFirst()) { //判断游标是否为空
            result = c.getString(c.getColumnIndex("status"));
            return result;
        } else {
            ContentValues cv = new ContentValues();
            cv.put("status", "true");
            db.insert("settings", null, cv);
            return "true";
        }

    }

    /**
     * 设置当前应用锁的开关状态。
     * @param status
     */
    public void setStatus(String status) {
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        db.insert("settings", null, cv);
        //修改条件
        String whereClause = "id=?";
        //修改添加参数
        String[] whereArgs = new String[]{String.valueOf("host")};
        //修改
        db.update("settings", cv, whereClause, whereArgs);

    }

    /**
     * 获取数据库当中的锁定密码
     * @return 锁定密码，String型的数据
     */
    public String getPWD() {
        String result;
        Cursor c = db.rawQuery("select * from settings", null);
        if (c.moveToFirst()) { //判断游标是否为空
            //System.out.println(c.getString(c.getColumnIndex("id")));
            if (c.getString(c.getColumnIndex("host")).equals("host")){
                result = c.getString(c.getColumnIndex("password"));
                return result;
            }else {
                return "null";
            }

        } else {
            return "null";
        }
    }

    /**
     *  设置锁定密码，并且将其存入数据库当中。
     * @param password
     * @return 插入则返回true的boolean型数据
     */
    public boolean setPWD(String password) {
        ContentValues cv = new ContentValues();
        cv.put("id", "host");
        cv.put("password", password);

        String[] args = {String.valueOf("host")};
        db.update("settings", cv, "id=?", args);
        return true;
    }

    /**
     * 获取数据库当中的Email
     * @return 返回string型的Email数据
     */
    public String getEmail() {
        String result;
        Cursor c = db.rawQuery("select * from settings", null);
        if (c.moveToFirst()) { //判断游标是否为空
            result = c.getString(c.getColumnIndex("email"));
            return result;
        } else {
            ContentValues cv = new ContentValues();
            cv.put("email", "null");
            db.insert("settings", null, cv);
            return "null";
        }
    }
}
