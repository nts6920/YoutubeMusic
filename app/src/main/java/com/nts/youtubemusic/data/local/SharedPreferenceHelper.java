package com.nts.youtubemusic.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import javax.inject.Inject;

public class SharedPreferenceHelper {
    private static final int DEFAULT_NUM = 0;
    private static final String DEFAULT_STRING = "";
    public static SharedPreferences sharedPreferences;


    @Inject
    public SharedPreferenceHelper(@NonNull Context context, String prefFileName) {
        sharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    public static void storeStringLanguage(Context context, String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }


    public static String getString(String key) {
        return sharedPreferences.getString(key, DEFAULT_STRING);
    }

    public static String getStringWithDefault(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void storeInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static int getInt(String key) {
        return sharedPreferences.getInt(key, DEFAULT_NUM);
    }

    public static int getIntWithDefault(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void storeLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public static long getLong(String key) {
        return sharedPreferences.getLong(key, DEFAULT_NUM);
    }


    public void storeBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }


    public static void storeFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0f);
    }


}


