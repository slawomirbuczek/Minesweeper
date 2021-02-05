package com.pk.minesweeper.client.service;

import android.content.Context;
import android.util.Log;

import com.pk.minesweeper.client.asynctask.statistics.GetStatisticsTask;
import com.pk.minesweeper.client.asynctask.statistics.PostStatisticsTask;
import com.pk.minesweeper.client.models.Statistics;
import com.pk.minesweeper.game.levels.Level;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class StatisticsService {

    public static Statistics getStatistics(Context context) {
        String jwt = Preferences.getJwt(context);
        GetStatisticsTask getStatisticsTask = new GetStatisticsTask(jwt);
        try {
            return getStatisticsTask.execute().get();
        } catch (Exception e) {
            Log.d("getStatsEx", e.getMessage());
            return new Statistics();
        }
    }

    public static void postStatistics(float time, Level level, Context context) {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        try {
            jsonObject.put("date", sdf.format(new Date()));
            jsonObject.put("time", time);
        } catch (JSONException e) {
            Log.i("postStatistics JsonEx", e.getMessage());
        }
        String jwt = Preferences.getJwt(context);
        PostStatisticsTask postStatisticsTask = new PostStatisticsTask(level, jwt);
        try {
            postStatisticsTask.execute(jsonObject).get();
        } catch (ExecutionException | InterruptedException e) {
            Log.d("postStatisticsEx", e.getMessage());
        }
    }

}
