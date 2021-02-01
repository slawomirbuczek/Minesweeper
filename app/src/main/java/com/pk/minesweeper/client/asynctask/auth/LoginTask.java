package com.pk.minesweeper.client.asynctask.auth;

import android.os.AsyncTask;
import android.util.Log;


import com.pk.minesweeper.client.model.LoginResponse;
import com.pk.minesweeper.client.model.ResponseMessage;

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

public class LoginTask extends AsyncTask<JSONObject, Void, LoginResponse> {


    @Override
    protected LoginResponse doInBackground(JSONObject... jsonObjects) {
        return login(jsonObjects[0]);
    }

    private LoginResponse login(JSONObject jsonObject) {
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(Collections.singletonList(MediaType.ALL));

            HttpEntity<?> httpEntity = new HttpEntity<>(jsonObject.toString(), requestHeaders);

            RestTemplate restTemplate = new RestTemplate(true);

            ResponseEntity<ResponseMessage> response = restTemplate.exchange(
                    "https://minesweeper-ranking.herokuapp.com/api/login",
                    HttpMethod.POST,
                    httpEntity,
                    ResponseMessage.class);

            String JWT = response.getHeaders().getAuthorization();
            ResponseMessage responseMessage = response.getBody();

            return new LoginResponse(responseMessage, JWT);
        } catch (HttpClientErrorException e) {
            String message = getExceptionMessage(e.getResponseBodyAsString());
            return new LoginResponse(new ResponseMessage(message), null);
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
