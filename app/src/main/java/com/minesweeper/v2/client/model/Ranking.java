package com.minesweeper.v2.client.model;

import java.time.LocalDateTime;

public class Ranking {

    private String username;

    private LocalDateTime date;

    private float time;

    public Ranking() {
    }

    public Ranking(String username, LocalDateTime date, float time) {
        this.username = username;
        this.date = date;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
