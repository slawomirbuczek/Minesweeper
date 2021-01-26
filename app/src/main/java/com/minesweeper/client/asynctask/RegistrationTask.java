package com.minesweeper.client.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.minesweeper.client.model.Message;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

public class RegistrationTask extends AsyncTask<JSONObject, Void, String> {

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {
        return register(jsonObjects[0]);
    }

    private String register(JSONObject jsonObjects) {
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(Collections.singletonList(MediaType.ALL));

            HttpEntity<?> httpEntity = new HttpEntity<>(jsonObjects.toString(), requestHeaders);

            RestTemplate restTemplate = new RestTemplate(true);

            ResponseEntity<Message> response = restTemplate.exchange(
                    "https://minesweeper-ranking.herokuapp.com/api/register",
                    HttpMethod.POST,
                    httpEntity,
                    Message.class);

            return response.getBody().getMessage();
        } catch (HttpClientErrorException e) {
            return getExceptionMessage(e.getResponseBodyAsString());
        }
    }

    private String getExceptionMessage(String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message;
        try {
            message = objectMapper.readValue(result, Message.class);
        } catch (IOException e) {
            Log.d("Validation Exception", e.getMessage());
            return "Registration failed";
        }
        return message.getMessage();
    }
}
