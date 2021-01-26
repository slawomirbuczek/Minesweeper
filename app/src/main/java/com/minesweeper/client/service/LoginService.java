package com.minesweeper.client.service;

import android.util.Log;

import com.minesweeper.client.asynctask.LoginTask;

import org.json.JSONObject;

public class LoginService {

    private static String JWT;

    public static boolean login(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            LoginTask loginTask = new LoginTask();
            JWT = loginTask.execute(jsonObject).get();
        } catch (Exception e) {
            Log.d("loginServiceEx", e.getMessage());
        }
        return JWT != null;
    }

    public static String getJWT() {
        return JWT;
    }

}
