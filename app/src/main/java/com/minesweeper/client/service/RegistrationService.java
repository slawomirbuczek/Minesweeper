package com.minesweeper.client.service;

import android.util.Log;

import com.minesweeper.client.asynctask.auth.RegistrationTask;

import org.json.JSONObject;

public class RegistrationService {

    public static String register(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            RegistrationTask registrationTask = new RegistrationTask();
            return registrationTask.execute(jsonObject).get();
        } catch (Exception e) {
            Log.d("registrationServiceEx", e.getMessage());
            return "Registration failed";
        }
    }
}
