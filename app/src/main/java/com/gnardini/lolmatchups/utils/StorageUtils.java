package com.gnardini.lolmatchups.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.gnardini.lolmatchups.LolMatchupsApp;
import com.google.gson.Gson;

/**
 * A bunch of shared preferences utils methods to get and set different types of values.
 */
public class StorageUtils {

    //Vars
    private static SharedPreferences sp =
            LolMatchupsApp.getContext().getSharedPreferences(
                    "LolMatchupsSharedPreferences",
                    Activity.MODE_PRIVATE);

    public static void storeInSharedPreferences(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static void storeInSharedPreferences(String key, Integer value) {
        sp.edit().putInt(key, value).apply();
    }

    public static void storeInSharedPreferences(String key, Float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public static void storeInSharedPreferences(String key, Boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static void storeInSharedPreferences(String key, Long value) {
        sp.edit().putLong(key, value).apply();
    }

    public static <T> void storeInSharedPreferences(String key, T value) {
        Gson gson = new Gson();
        storeInSharedPreferences(key, gson.toJson(value));
    }

    public static String getStringFromSharedPreferences(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static Integer getIntFromSharedPreferences(String key, Integer defValue) {
        return sp.getInt(key, defValue);
    }

    public static Float getFloatFromSharedPreferences(String key, Float defValue) {
        return sp.getFloat(key, defValue);
    }

    public static Boolean getBooleanFromSharedPreferences(String key, Boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static Long getLongFromSharedPreferences(String key, Long defValue) {
        return sp.getLong(key, defValue);
    }

    public static <T> T getObjectFromSharedPreferences(String key, Class<T> clazz) {
        String stringValue = getStringFromSharedPreferences(key, null);
        Gson gson = new Gson();
        return gson.fromJson(stringValue, clazz);
    }

    public static void clearKey(String key) {
        sp.edit().remove(key).apply();
    }

    public static boolean keyExists(String key) {
        return sp.contains(key);
    }

}

