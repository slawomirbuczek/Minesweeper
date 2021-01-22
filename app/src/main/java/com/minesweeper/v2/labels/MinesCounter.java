package com.minesweeper.v2.labels;

import android.widget.ImageView;

public class MinesCounter extends Label {

    private int minesLeft;

    public MinesCounter(ImageView image_first, ImageView image_second, ImageView image_third) {
        super(image_first, image_second, image_third);
    }

    public void setMinesLeft(int mines) {
        this.minesLeft = mines;
        setLabel(mines);
    }

    public void increment() {
        minesLeft++;
        if (minesLeft >= 0) {
            setLabel(minesLeft);
        }
    }

    public void decrement() {
        minesLeft--;
        if (minesLeft >= 0) {
            setLabel(minesLeft);
        }
    }
}
