package com.pk.minesweeper.client.asynctask.statistics;

import android.os.AsyncTask;
import android.util.Log;

import com.pk.minesweeper.client.models.RankingRecord;
import com.pk.minesweeper.game.levels.Level;

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

public class PostStatisticsTask extends AsyncTask<JSONObject, Void, Void> {

    private final Level level;
    private final String JWT;

    public PostStatisticsTask(Level level, String jwt) {
        this.level = level;
        JWT = jwt;
    }

    @Override
    protected Void doInBackground(JSONObject... jsonObjects) {
        postToStats(jsonObjects[0]);
        return null;
    }

    private void postToStats(JSONObject jsonObject) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", JWT);
            httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);

            RestTemplate restTemplate = new RestTemplate(true);
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            restTemplate.exchange(
                    "https://minesweeper-ranking.herokuapp.com/api/statistics/" + level.name(),
                    HttpMethod.POST,
                    httpEntity,
                    RankingRecord.class
            );

        } catch (HttpStatusCodeException e) {
            Log.d("postRecordEx", e.getResponseBodyAsString() + " " + e.getStatusText());
        }
    }

}
