package com.minesweeper.client.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class LoginTask extends AsyncTask<JSONObject, Void, String> {

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {
        return login(jsonObjects[0]);
    }

    private String login(JSONObject jsonObject) {
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(Collections.singletonList(MediaType.ALL));

            RestTemplate restTemplate = new RestTemplate(true);

            ResponseEntity<?> response = restTemplate.exchange(
                    "https://minesweeper-ranking.herokuapp.com/api/login",
                    HttpMethod.POST,
                    new HttpEntity<>(jsonObject.toString(), requestHeaders),
                    Object.class);

            return response.getHeaders().getAuthorization();
        } catch (HttpStatusCodeException e) {
            Log.d("loginREST", e.getResponseBodyAsString());
        }
        return null;
    }

}
