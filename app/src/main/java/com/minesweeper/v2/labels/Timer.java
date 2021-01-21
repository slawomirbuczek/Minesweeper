package com.minesweeper.v2.labels;

import android.os.Handler;
import android.widget.ImageView;

public class Timer extends Label {

    private Runnable runnable;
    private Handler handler;
    private int time;

    public Timer(ImageView image_first, ImageView image_second, ImageView image_third) {
        super(image_first, image_second, image_third);

        time = 0;

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
    }

    public void stop() {
        handler.removeCallbacks(runnable);
    }

    public void resume() {
        if (time != 0)
            handler.post(runnable);
    }

    public void reset() {
        stop();
        resetLabel();
    }

    public int getTime() {
        return time;
    }

}
