package com.minesweeper.client.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.minesweeper.client.model.Message;
import com.minesweeper.client.service.LoginService;
import com.minesweeper.game.levels.Level;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class PostGlobalRankingRecordTask extends AsyncTask<JSONObject, Void, Void> {

    private final Level level;

    public PostGlobalRankingRecordTask(Level level) {
        this.level = level;
    }

    @Override
    protected Void doInBackground(JSONObject... jsonObjects) {
        postRecord(jsonObjects[0]);
        return null;
    }

    private void postRecord(JSONObject jsonObject) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", LoginService.getJWT());
            httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);

            Log.i("getRankingEntity", httpEntity.toString());

            String RANKING_URL = "https://minesweeper-ranking.herokuapp.com/api/ranking/";

            RestTemplate restTemplate = new RestTemplate(true);
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            ResponseEntity<Message> response = restTemplate.exchange(
                    RANKING_URL + level.name(),
                    HttpMethod.POST,
                    httpEntity,
                    Message.class
            );

            Log.i("Post record response", response.getBody().getMessage());
        } catch (HttpStatusCodeException e) {
            Log.d("postRecordEx", e.getResponseBodyAsString() + " " + e.getStatusText());
        }
    }

}
