package com.pk.minesweeper.client.models;

public class Statistics {

    private String username;

    private StatisticsDto statisticsEasy;

    private StatisticsDto statisticsMedium;

    private StatisticsDto statisticsHard;

    public Statistics() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StatisticsDto getStatisticsEasy() {
        return statisticsEasy;
    }

    public void setStatisticsEasy(StatisticsDto statisticsEasy) {
        this.statisticsEasy = statisticsEasy;
    }

    public StatisticsDto getStatisticsMedium() {
        return statisticsMedium;
    }

    public void setStatisticsMedium(StatisticsDto statisticsMedium) {
        this.statisticsMedium = statisticsMedium;
    }

    public StatisticsDto getStatisticsHard() {
        return statisticsHard;
    }

    public void setStatisticsHard(StatisticsDto statisticsHard) {
        this.statisticsHard = statisticsHard;
    }
}
