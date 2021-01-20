package com.minesweeper.logic.labels;

import android.os.Handler;
import android.widget.ImageView;

public class Timer extends Label {
    private Handler handler;
    private Runnable runnable;

    public Timer(ImageView imageX, ImageView imageY, ImageView imageZ) {
        super(imageX, imageY, imageZ);
        handler = new Handler();
        runnable = new Runnable()
        {
            public void run()
            {
                if(number < 999) {
                    handler.postDelayed(this, 1000);
                    set(++number);
                }
            }
        };
    }

    public void start(){
        number = 0;
        handler.post(runnable);
    }

    public void stop(){
        handler.removeCallbacks(runnable);
    }

    public void resume(){
        if(number != 0)
            handler.post(runnable);
    }

    public void reset(){
        stop();
        resetLabel();
    }

    public int getTime(){
        return number;
    }
}
