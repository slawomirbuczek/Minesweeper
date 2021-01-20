package com.minesweeper.logic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseRanking {
    private DatabaseHelper databaseHelper;
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

    public void addSet(String nickname, int time, String level) {
        databaseRanking = databaseHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.RANKING_NICKNAME, nickname);
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

    public Cursor getRanking(String level) {
        return databaseRanking.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_RANKING +
                " WHERE " + DatabaseHelper.RANKING_LEVEL + " = '" + level + "' ORDER BY " + DatabaseHelper.RANKING_TIME + " ASC", null);
    }
}
