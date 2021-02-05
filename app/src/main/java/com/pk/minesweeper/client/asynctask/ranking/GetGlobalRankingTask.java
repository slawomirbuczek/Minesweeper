package com.pk.minesweeper.client.asynctask.ranking;

import android.os.AsyncTask;
import android.util.Log;


import com.pk.minesweeper.client.models.RankingRecord;
import com.pk.minesweeper.game.levels.Level;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class GetGlobalRankingTask extends AsyncTask<Level, Void, RankingRecord[]> {

    private final String JWT;

    public GetGlobalRankingTask(String jwt) {
        JWT = jwt;
    }

    @Override
    protected RankingRecord[] doInBackground(Level... levels) {
        return getRanking(levels[0]);
    }

    private RankingRecord[] getRanking(Level level) {
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", JWT);
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);

            RestTemplate restTemplate = new RestTemplate(true);
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            ResponseEntity<RankingRecord[]> response = restTemplate.exchange(
                    "https://minesweeper-ranking.herokuapp.com/api/ranking/" + level.toString(),
                    HttpMethod.GET,
                    httpEntity,
                    RankingRecord[].class
            );

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            Log.d("GlobalRankingService RestClientException", e.getResponseBodyAsString() + " " + e.getStatusText());
        }
        return new RankingRecord[]{};
    }

}
