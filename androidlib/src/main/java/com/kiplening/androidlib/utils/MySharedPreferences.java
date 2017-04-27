package com.kiplening.androidlib.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MOON on 11/16/2016.
 */

public class MySharedPreferences {
    private SharedPreferences mSharedPreferences ;
    public MySharedPreferences(Context context, String name){
        this(context,name,Context.MODE_PRIVATE);
    }
    public MySharedPreferences(Context context, String name, int mode){
        mSharedPreferences = context.getSharedPreferences(name,mode);
    }

    /**
     * 将字符串保存到 SharedPreferences 中
     * @param key
     * @param value
     */
    public void save(String key, String value){
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * 取出 SharedPreferences 中的字符串
     * @param key
     * @return
     */
    public String load(String key){
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 取出 SharedPreferences 中的内容，并反序列化为对象
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T load(String key, Class<T> cls){
        String mStr = mSharedPreferences.getString(key,"");
        if (mStr != null){
            return JsonUtils.jsonToObject(mStr, cls);
        }
        return null;
    }
}
