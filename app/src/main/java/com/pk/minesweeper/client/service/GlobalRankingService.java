package com.pk.minesweeper.client.service;

import android.content.Context;
import android.util.Log;


import com.pk.minesweeper.client.asynctask.ranking.GetGlobalRankingTask;
import com.pk.minesweeper.client.asynctask.ranking.PostGlobalRankingRecordTask;
import com.pk.minesweeper.client.model.RankingRecord;
import com.pk.minesweeper.game.levels.Level;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GlobalRankingService {

    public static void postRecord(float time, Level level, Context context) {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        try {
            jsonObject.put("date", sdf.format(new Date()));
            jsonObject.put("time", time);
        } catch (JSONException e) {
            Log.i("postRecord JsonEx", e.getMessage());
        }

        String jwt = Preferences.getJwt(context);

        PostGlobalRankingRecordTask postGlobalRankingRecordTask = new PostGlobalRankingRecordTask(level, jwt);
        postGlobalRankingRecordTask.execute(jsonObject);
    }

    public static RankingRecord[] getRanking(Level level, Context context) {
        String jwt = Preferences.getJwt(context);
        GetGlobalRankingTask getGlobalRankingTask = new GetGlobalRankingTask(jwt);
        try {
            return getGlobalRankingTask.execute(level).get();
        } catch (Exception e) {
            Log.d("getRankingEx", e.getMessage());
            return new RankingRecord[]{};
        }
    }

}
