package com.minesweeper.client.service;

import android.location.Location;
import android.util.Log;

import com.minesweeper.client.asynctask.GetGlobalRankingTask;
import com.minesweeper.client.asynctask.PostGlobalRankingRecordTask;
import com.minesweeper.client.model.RankingRecord;
import com.minesweeper.game.levels.Level;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GlobalRankingService {

    public static void postRecord(float time, Level level) {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        try {
            jsonObject.put("date", sdf.format(new Date()));
            jsonObject.put("time", time);
        } catch (JSONException e) {
            Log.i("postRecord JsonEx", e.getMessage());
        }
        PostGlobalRankingRecordTask postGlobalRankingRecordTask = new PostGlobalRankingRecordTask(level);
        postGlobalRankingRecordTask.execute(jsonObject);
    }

    public static RankingRecord[] getRanking(Level level) {
        GetGlobalRankingTask getGlobalRankingTask = new GetGlobalRankingTask();
        try {
            return getGlobalRankingTask.execute(level).get();
        } catch (Exception e) {
            Log.d("getRankingEx", e.getMessage());
            return new RankingRecord[]{};
        }
    }

}
