package com.minesweeper.client.service;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String PREF_NAME = "pref";

    public static void addUsername(String username, Context context) {
        addStringToPreferences("username", username, context);
    }

    public static void addPassword(String password, Context context) {
        addStringToPreferences("password", password, context);
    }

    public static void addJwt(String jwt, Context context) {
        addStringToPreferences("jwt", jwt, context);
    }

    private static void addStringToPreferences(String key, String data, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public static String getUsername(Context context) {
        return getStringFromPreferences("username", context);
    }

    public static String getPassword(Context context) {
        return getStringFromPreferences("password", context);
    }

    public static String getJwt(Context context) {
        String jwt =  getStringFromPreferences("jwt", context);

        if (jwt == null) {
            LoginService.login(getUsername(context), getPassword(context), context);
        }

        return jwt;
    }

    private static String getStringFromPreferences(String key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

}
