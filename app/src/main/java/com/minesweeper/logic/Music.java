package com.minesweeper.logic;

import android.app.Activity;
import android.media.MediaPlayer;

import com.minesweeper.R;

public class Music {
    private static MediaPlayer mediaPlayer;
    private static boolean ON = false;
    private static boolean initialized = false;

    public static void initialize(Activity activity) {
        mediaPlayer = MediaPlayer.create(activity, R.raw.music);
        mediaPlayer.setLooping(true);
        initialized = true;
    }

    public static void play() {
        ON = true;
        mediaPlayer.start();
    }

    public static void stop() {
        ON = false;
        mediaPlayer.pause();
    }

    public static void pause() {
        if (ON)
            mediaPlayer.pause();
    }

    public static void resume() {
        if (ON)
            mediaPlayer.start();
    }

    public static boolean isInitialized() {
        return initialized;
    }


    public static boolean isON() {
        return ON;
    }
}
