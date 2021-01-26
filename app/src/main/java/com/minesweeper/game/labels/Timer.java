package com.minesweeper.game.labels;

import android.os.Handler;
import android.widget.ImageView;

public class Timer extends Label {

    private Runnable runnable;
    private Handler handler;
    private int time;
    private long startTime;
    private long estimatedTime;

    public Timer(ImageView image_first, ImageView image_second, ImageView image_third) {
        super(image_first, image_second, image_third);

        time = 0;
        startTime = 0;
        estimatedTime = 0;

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (time < 999) {
                    setLabel(time);
                    time++;
                    handler.postDelayed(this, 1000);
                }
            }
        };

    }

    public void start() {
        handler.post(runnable);
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        handler.removeCallbacks(runnable);
        estimatedTime += System.currentTimeMillis() - startTime;
    }

    public void resume() {
        if (time != 0) {
            handler.post(runnable);
            startTime = System.currentTimeMillis();
        }
    }

    public void reset() {
        stop();
        resetLabel();
        time = 0;
        estimatedTime = 0;
    }

    public float getTime() {
        return (float) (estimatedTime / 1000.);
    }

}
