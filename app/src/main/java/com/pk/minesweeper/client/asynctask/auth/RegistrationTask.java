package com.pk.minesweeper.client.asynctask.auth;

import android.os.AsyncTask;
import android.util.Log;

import com.pk.minesweeper.client.models.ResponseMessage;

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

            ResponseEntity<ResponseMessage> response = restTemplate.exchange(
                    "https://minesweeper-ranking.herokuapp.com/api/register",
                    HttpMethod.POST,
                    httpEntity,
                    ResponseMessage.class);

            return response.getBody().getMessage();
        } catch (HttpClientErrorException e) {
            return getExceptionMessage(e.getResponseBodyAsString());
        }
    }

    private String getExceptionMessage(String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseMessage responseMessage;
        try {
            responseMessage = objectMapper.readValue(result, ResponseMessage.class);
        } catch (IOException e) {
            Log.d("Validation Exception", e.getMessage());
            return "Registration failed";
        }
        return responseMessage.getMessage();
    }
}
