package com.pk.minesweeper.client.models;

public class Statistics {

    private String username;

    private StatisticsDto easy;

    private StatisticsDto medium;

    private StatisticsDto hard;

    public Statistics() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StatisticsDto getEasy() {
        return easy;
    }

    public void setEasy(StatisticsDto easy) {
        this.easy = easy;
    }

    public StatisticsDto getMedium() {
        return medium;
    }

    public void setMedium(StatisticsDto medium) {
        this.medium = medium;
    }

    public StatisticsDto getHard() {
        return hard;
    }

    public void setHard(StatisticsDto hard) {
        this.hard = hard;
    }
}
