package com.kiplening.androidlib.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by MOON on 9/13/2016.
 */
public class JsonUtils {

    private static Gson mGson = new Gson();

    /**
     * 对象转换为Json
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String objectToJson(T object){
        return mGson.toJson(object);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json对象转换为实体对象
     * @param json
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T jsonToObject(JsonObject json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }
}
