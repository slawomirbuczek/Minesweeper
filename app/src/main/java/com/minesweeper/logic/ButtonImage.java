package com.minesweeper.logic;

import com.minesweeper.R;

import java.util.HashMap;
import java.util.Map;

public enum  ButtonImage {

    NUMBER_ZERO(0, R.drawable.type0),
    NUMBER_ONE(1, R.drawable.type1),
    NUMBER_TWO(2, R.drawable.type2),
    NUMBER_THREE(3, R.drawable.type3),
    NUMBER_FOUR(4, R.drawable.type4),
    NUMBER_FIVE(5, R.drawable.type5),
    NUMBER_SIX(6, R.drawable.type6),
    NUMBER_SEVEN(7, R.drawable.type7),
    NUMBER_EIGHT(8, R.drawable.type8),
    MINE(9, R.drawable.mine);

    private int number;
    private int imageId;

    ButtonImage(int number, int imageId) {
    }

    private static final Map<Integer, Integer> lookup = new HashMap<>();

    static {
        for (ButtonImage b : ButtonImage.values()) {
            lookup.put(b.number, b.imageId);
        }
    }

    public static int getImage(int number) {
        return lookup.get(number);
    }

}
