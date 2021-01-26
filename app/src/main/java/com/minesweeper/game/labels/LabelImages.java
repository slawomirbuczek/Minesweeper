package com.minesweeper.game.labels;

import com.minesweeper.R;

public enum LabelImages {

    ZERO('0', R.drawable.d0),
    ONE('1', R.drawable.d1),
    TWO('2', R.drawable.d2),
    THREE('3', R.drawable.d3),
    FOUR('4', R.drawable.d4),
    FIVE('5', R.drawable.d5),
    SIX('6', R.drawable.d6),
    SEVEN('7', R.drawable.d7),
    EIGHT('8', R.drawable.d8),
    NINE('9', R.drawable.d9);

    private char number;
    private int imageId;

    LabelImages(char number, int imageId) {
        this.number = number;
        this.imageId = imageId;
    }

    public static int getImageId(char number) {
        for (LabelImages value : values()) {
            if (value.number == number) {
                return value.imageId;
            }
        }
        return R.drawable.d0;
    }
}
