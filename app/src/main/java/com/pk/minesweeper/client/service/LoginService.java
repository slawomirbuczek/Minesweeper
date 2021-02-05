package com.pk.minesweeper.client.service;

import android.content.Context;


import com.pk.minesweeper.client.asynctask.auth.LoginTask;
import com.pk.minesweeper.client.models.LoginResponse;

import org.json.JSONObject;

public class LoginService {

    public static String login(String username, String password, Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            LoginTask loginTask = new LoginTask();
            LoginResponse response = loginTask.execute(jsonObject).get();
            Preferences.addJwt(response.getJWT(), context);
            return response.getResponseMessage().getMessage();
        } catch (Exception e) {
            return "JSONObject error";
        }
    }

}
