package com.pk.minesweeper.client.models;

import java.util.Locale;

public class StatisticsDto {

    private int totalGamesPlayed;

    private int gamesWon;

    private float totalTime;

    private float averageTime;

    private float bestTime;

    public StatisticsDto() {

    }

    public String getTotalGamesPlayedAsString() {
        return String.valueOf(totalGamesPlayed);
    }

    public String getGamesWonAsString() {
        return String.valueOf(gamesWon);
    }

    public String getWinRatioAsString() {
        float winRatio = 100f * gamesWon / totalGamesPlayed;
        winRatio = Float.isNaN(winRatio) ? 0 : winRatio;
        return String.format(Locale.ENGLISH, "%.2f", winRatio);
    }

    public String getTotalTimeAsString() {
        return String.format(Locale.ENGLISH, "%.2f", totalTime);
    }

    public String getAverageTimeAsString() {
        return String.format(Locale.ENGLISH, "%.2f", averageTime);
    }

    public String getBestTimeAsString() {
        return String.format(Locale.ENGLISH, "%.2f", bestTime);
    }


    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public void setAverageTime(float averageTime) {
        this.averageTime = averageTime;
    }

    public void setBestTime(float bestTime) {
        this.bestTime = bestTime;
    }
}
