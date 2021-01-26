package com.minesweeper.client.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RankingRecord {

    private String username;

    private Date date;

    private float time;

    public RankingRecord() {
    }

    public RankingRecord(String username, Date date, float time) {
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

    public Date getDate() {
        return date;
    }

    public String getDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTime() {
        return time;
    }

    public String getTimeAsString() {
        return String.format(Locale.ENGLISH,"%.2f", this.time);
    }

    public void setTime(float time) {
        this.time = time;
    }
}
