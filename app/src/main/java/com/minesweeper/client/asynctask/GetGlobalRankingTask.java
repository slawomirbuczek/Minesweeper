package com.minesweeper.client.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.minesweeper.client.model.RankingRecord;
import com.minesweeper.client.service.LoginService;
import com.minesweeper.game.levels.Level;

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

    @Override
    protected RankingRecord[] doInBackground(Level... levels) {
        return getRanking(levels[0]);
    }

    private RankingRecord[] getRanking(Level level) {
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", LoginService.getJWT());
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);

            Log.i("getRankingEntity", httpEntity.toString());
            String RANKING_URL = "https://minesweeper-ranking.herokuapp.com/api/ranking/";

            RestTemplate restTemplate = new RestTemplate(true);
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            ResponseEntity<RankingRecord[]> response = restTemplate.exchange(RANKING_URL + level.toString(), HttpMethod.GET, httpEntity, RankingRecord[].class);

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            Log.d("GlobalRankingService RestClientException", e.getResponseBodyAsString() + " " + e.getStatusText());
        }
        return new RankingRecord[]{};
    }

}
