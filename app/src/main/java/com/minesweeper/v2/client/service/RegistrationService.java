package com.minesweeper.v2.client.service;

import android.os.AsyncTask;
import android.util.Log;

import com.minesweeper.v2.client.model.LoginCredentials;
import com.minesweeper.v2.client.model.Message;

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

public class RegistrationService extends AsyncTask<LoginCredentials, Void, String> {

    @Override
    protected String doInBackground(LoginCredentials... loginCredentials) {
        String username = loginCredentials[0].getUsername();
        String password = loginCredentials[0].getPassword();
        return register(username, password);
    }

    private String register(String username, String password) {
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
            String REGISTRATION_URL = "https://minesweeper-ranking.herokuapp.com/api/register";
            ResponseEntity<Message> response = restTemplate.exchange(REGISTRATION_URL, HttpMethod.POST, httpEntity, Message.class);
            Log.i("Registration message", response.getBody().getMessage());
            return response.getBody().getMessage();
        } catch (RestClientException | JSONException e) {
            Log.d("Registration exception", e.getMessage());
        }
        return "Cannot connect to the server";
    }
}
