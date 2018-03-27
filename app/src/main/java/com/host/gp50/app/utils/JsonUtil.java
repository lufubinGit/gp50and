package com.host.gp50.app.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json字符串工具类
 * Created by jd on 2017/7/10.
 */

public class JsonUtil {

    /**
     * 获取Json字符串键对应的值
     * @param json    Json字符串
     * @param key       键
     * @return          值
     */
    public static String getValueByKey(String json,String key) {
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取Json字符串键对应的值
     * @param json    Json字符串
     * @param key       键
     * @return          值
     */
    public static int getIntValueByKey(String json,String key) {
        int value = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            value = jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取Json字符串键对应的值
     * @param json    Json字符串
     * @param key       键
     * @return          值
     */
    public static boolean getBooleanValueByKey(String json,String key) {
        boolean value = false;
        try {
            JSONObject jsonObject = new JSONObject(json);
            value = jsonObject.getBoolean(key);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 把一个map变成json字符串
     * @param map
     * @return
     */
    public static String parseMapToJson(Map<?, ?> map) {
        try {
            Gson gson = new Gson();
            return gson.toJson(map);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 把一个json字符串变成对象
     * @param json
     * @param cls
     * @return
     */
    public static <T> T parseJsonToBean(String json, Class<T> cls) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * 把json字符串变成map
     * @param json
     * @return
     */
    public static HashMap<String, Object> parseJsonToMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> map = null;
        try {
            map = gson.fromJson(json, type);
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type  new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static List<?> parseJsonToList(String json, Type type) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, type);
        return list;
    }

    /**
     *
     * 获取json串中某个字段的值，注意，只能获取同一层级的value
     *
     * @param json
     * @param key
     * @return
     */
    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static <T> List<T> getJavaArray(String json,Class<T> clazz) {
        return JSONArray.parseArray(json, clazz);
    }
}
