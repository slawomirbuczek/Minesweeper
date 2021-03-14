package com.pk.minesweeper.game.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pk.minesweeper.R;
import com.pk.minesweeper.client.models.Statistics;
import com.pk.minesweeper.client.models.StatisticsDto;
import com.pk.minesweeper.client.service.StatisticsService;

public class StatisticsController extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        findViewById(R.id.statistics_back_button).setOnClickListener(this);
        getStatistics();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.statistics_back_button) {
            startActivity(new Intent(this, MenuController.class));
        }
    }

    private void getStatistics() {
        Statistics statistics = StatisticsService.getStatistics(this);
        ((TextView) findViewById(R.id.statistics_username))
                .append(statistics.getUsername());

        StatisticsDto statisticsEasy = statistics.getEasy();
        StatisticsDto statisticsMedium = statistics.getMedium();
        StatisticsDto statisticsHard = statistics.getHard();

        ((TextView) findViewById(R.id.statistics_easy_games))
                .append(statisticsEasy.getTotalGamesPlayedAsString());
        ((TextView) findViewById(R.id.statistics_medium_games))
                .append(statisticsMedium.getTotalGamesPlayedAsString());
        ((TextView) findViewById(R.id.statistics_hard_games))
                .append(statisticsHard.getTotalGamesPlayedAsString());

        ((TextView) findViewById(R.id.statistics_easy_wins))
                .append(statisticsEasy.getGamesWonAsString());
        ((TextView) findViewById(R.id.statistics_medium_wins))
                .append(statisticsMedium.getGamesWonAsString());
        ((TextView) findViewById(R.id.statistics_hard_wins))
                .append(statisticsHard.getGamesWonAsString());

        ((TextView) findViewById(R.id.statistics_easy_total_time))
                .append(statisticsEasy.getTotalTimeAsString());
        ((TextView) findViewById(R.id.statistics_medium_total_time))
                .append(statisticsMedium.getTotalTimeAsString());
        ((TextView) findViewById(R.id.statistics_hard_total_time))
                .append(statisticsHard.getTotalTimeAsString());

        ((TextView) findViewById(R.id.statistics_easy_avg_time))
                .append(statisticsEasy.getAverageTimeAsString());
        ((TextView) findViewById(R.id.statistics_medium_avg_time))
                .append(statisticsMedium.getAverageTimeAsString());
        ((TextView) findViewById(R.id.statistics_hard_avg_time))
                .append(statisticsHard.getAverageTimeAsString());

        ((TextView) findViewById(R.id.statistics_easy_best_time))
                .append(statisticsEasy.getBestTimeAsString());
        ((TextView) findViewById(R.id.statistics_medium_best_time))
                .append(statisticsMedium.getBestTimeAsString());
        ((TextView) findViewById(R.id.statistics_hard_best_time))
                .append(statisticsHard.getBestTimeAsString());

        ((TextView) findViewById(R.id.statistics_easy_win_ratio))
                .append(String.valueOf(statisticsEasy.getWinRatioAsString()));
        ((TextView) findViewById(R.id.statistics_medium_win_ratio))
                .append(String.valueOf(statisticsMedium.getWinRatioAsString()));
        ((TextView) findViewById(R.id.statistics_hard_win_ratio))
                .append(String.valueOf(statisticsHard.getWinRatioAsString()));
    }
}
