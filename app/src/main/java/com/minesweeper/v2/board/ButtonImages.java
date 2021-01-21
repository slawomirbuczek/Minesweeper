package com.minesweeper.v2.board;

import com.minesweeper.R;

public enum ButtonImages {

    NUMBER_ZERO(0, R.drawable.type0),
    NUMBER_ONE(1, R.drawable.type1),
    NUMBER_TWO(2, R.drawable.type2),
    NUMBER_THREE(3, R.drawable.type3),
    NUMBER_FOUR(4, R.drawable.type4),
    NUMBER_FIVE(5, R.drawable.type5),
    NUMBER_SIX(6, R.drawable.type6),
    NUMBER_SEVEN(7, R.drawable.type7),
    NUMBER_EIGHT(8, R.drawable.type8),
    MINE(9, R.drawable.mine),
    FLAG(10, R.drawable.flag),
    BUTTON(11, R.drawable.button),
    RED_MINE(12, R.drawable.mine_red),
    WRONG_MINE(13, R.drawable.mine_wrong);

    private int number;
    private int imageId;

    ButtonImages(int number, int imageId) {
        this.number = number;
        this.imageId = imageId;
    }


    public static int getImageId(int number) {
        for (ButtonImages value : values()) {
            if (value.number == number) {
                return value.imageId;
            }
        }
        return R.drawable.button;
    }


    public int getImageId() {
        return this.imageId;
    }

}
