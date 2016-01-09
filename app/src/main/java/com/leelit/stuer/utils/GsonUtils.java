package com.leelit.stuer.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leelit.stuer.bean.CarpoolingInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Leelit on 2016/1/7.
 */
public class GsonUtils {

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    /**
     * can't work
     */
    public static <T> LinkedList<T> fromJsonArray(String json, final Class<T> clazz) {
        Type type = new TypeToken<LinkedList<T>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static ArrayList<CarpoolingInfo> fromJsonArray(String jsonArray) {
        Type type = new TypeToken<ArrayList<CarpoolingInfo>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonArray, type);
    }

    public static ArrayList<ArrayList<CarpoolingInfo>> fromJsonArrayArr(String jsonArray) {
        Type type = new TypeToken<ArrayList<ArrayList<CarpoolingInfo>>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonArray, type);
    }
}
