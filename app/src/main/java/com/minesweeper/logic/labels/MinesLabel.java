package com.minesweeper.logic.labels;

import android.widget.ImageView;

import com.minesweeper.R;
import com.minesweeper.logic.BoardButton;

public class MinesLabel extends Label {

    public MinesLabel(ImageView imageX, ImageView imageY, ImageView imageZ) {
        super(imageX, imageY, imageZ);
    }

    public void resetAndSetMines(int mines){
        number = mines;
        set(number);
    }

    public void setFlag(BoardButton button){
        if(number > 0){
            set(--number);
            button.setFlagged(true);
            button.getButton().setBackgroundResource(R.drawable.flag);
        }
    }

    public void removeFlag(BoardButton button) {
        set(++number);
        button.setFlagged(false);
        button.getButton().setBackgroundResource(R.drawable.button);
    }
}
