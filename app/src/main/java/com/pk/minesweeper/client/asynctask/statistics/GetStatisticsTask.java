package com.pk.minesweeper.client.asynctask.statistics;

import android.os.AsyncTask;
import android.util.Log;

import com.pk.minesweeper.client.models.Statistics;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class GetStatisticsTask extends AsyncTask<Void, Void, Statistics> {

    private final String JWT;

    public GetStatisticsTask(String jwt) {
        JWT = jwt;
    }


    @Override
    protected Statistics doInBackground(Void... voids) {
        return getStatistics();
    }

    private Statistics getStatistics() {
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", JWT);
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);

            RestTemplate restTemplate = new RestTemplate(true);
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            ResponseEntity<Statistics> response = restTemplate.exchange(
                    "https://minesweeper-ranking.herokuapp.com/api/statistics/",
                    HttpMethod.GET,
                    httpEntity,
                    Statistics.class
            );

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            Log.d("GlobalRankingService RestClientException", e.getResponseBodyAsString() + " " + e.getStatusText());
            return null;
        }
    }

}
