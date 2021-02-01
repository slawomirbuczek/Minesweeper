package com.pk.minesweeper.game.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pk.minesweeper.game.levels.Level;

public class DatabaseRanking {
    private final DatabaseHelper databaseHelper;
    private SQLiteDatabase databaseRanking;

    public DatabaseRanking(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void open() {
        databaseRanking = databaseHelper.getReadableDatabase();
    }

    public void close() {
        databaseRanking.close();
    }

    public void addSet(String date, float time, String level) {
        databaseRanking = databaseHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.RANKING_DATE, date);
        cv.put(DatabaseHelper.RANKING_TIME, time);
        cv.put(DatabaseHelper.RANKING_LEVEL, level);
        try {
            databaseRanking.insert(DatabaseHelper.TABLE_RANKING,
                    null, cv);
        } catch (SQLException e) {
            Log.i("DB write error", e.getMessage());
        }

        databaseRanking.close();
    }

    public Cursor getRanking(Level level) {
        return databaseRanking.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_RANKING +
                " WHERE " + DatabaseHelper.RANKING_LEVEL + " = '" + level.name() + "' ORDER BY " + DatabaseHelper.RANKING_TIME + " ASC", null);
    }
}
