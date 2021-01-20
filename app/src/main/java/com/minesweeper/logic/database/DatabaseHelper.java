package com.minesweeper.logic.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Baza danych SQLite.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Ranking.db";
    private static final int DB_VERSION = 1;

    static final String TABLE_RANKING = "RANKING";
    static final String RANKING_NICKNAME = "nickname";
    static final String RANKING_TIME = "time";
    static final String RANKING_LEVEL = "level";

    private static final String CREATE_TABLE_RANKING =
            "CREATE TABLE " + TABLE_RANKING + "(" +
                    RANKING_NICKNAME + " TEXT," +
                    RANKING_TIME + " INTEGER," +
                    RANKING_LEVEL + " TEXT" + ")";


    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_RANKING);
        } catch (SQLException ex) {
            Log.i("SQLite error", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANKING);
            onCreate(db);
        } catch (SQLException ex) {
            Log.i("SQLite error", ex.getMessage());
        }
    }
}
