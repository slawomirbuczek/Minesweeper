package com.minesweeper.v2.client.service;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.minesweeper.v2.client.model.LoginCredentials;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class LoginService extends AsyncTask<LoginCredentials, Void, Boolean> {

    private static String JWT;

    @Override
    protected Boolean doInBackground(LoginCredentials... loginCredentials) {
        String username = loginCredentials[0].getUsername();
        String password = loginCredentials[0].getPassword();
        return login(username, password);
    }

    public boolean login(String username, String password) {
        try {
            JSONObject jsonCredentials = new JSONObject();
            jsonCredentials.put("username", username);
            jsonCredentials.put("password", password);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(Collections.singletonList(MediaType.ALL));

            RestTemplate restTemplate = new RestTemplate(true);

            HttpEntity<?> httpEntity = new HttpEntity<>(jsonCredentials.toString(), requestHeaders);
            Log.i("httpEntity", httpEntity.toString());
            String LOGIN_URL = "https://minesweeper-ranking.herokuapp.com/api/login";
            ResponseEntity<?> response = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, httpEntity, Object.class);
            JWT = response.getHeaders().getAuthorization();
            Log.i("JWT", JWT);

        } catch (RestClientException | JSONException e) {
            Log.d("Login exception", e.getMessage());
            return false;
        }
        return true;
    }

    public static String getJWT() {
        return JWT;
    }
}
