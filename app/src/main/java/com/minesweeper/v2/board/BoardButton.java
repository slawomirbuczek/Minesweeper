package com.minesweeper.v2.board;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BoardButton extends androidx.appcompat.widget.AppCompatButton {

    private int number;
    private int numberInRow;
    private int numberInColumn;
    private boolean flagged;
    private boolean disabled;

    public BoardButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BoardButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardButton(@NonNull Context context) {
        super(context);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setBoardButton(int numberInRow, int numberInColumn) {
        this.numberInRow = numberInRow;
        this.numberInColumn = numberInColumn;
        this.flagged = false;
        this.setId(View.generateViewId());
        this.number = 0;
        this.setDefaultButton();
    }

    public void reset() {
        number = 0;
        flagged = false;
        disabled = false;
        setDefaultButton();
    }

    public void changeImage() {
        this.setBackgroundResource(ButtonImages.getImageId(number));
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberInRow() {
        return numberInRow;
    }

    public int getNumberInColumn() {
        return numberInColumn;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlag() {
        this.setBackgroundResource(ButtonImages.FLAG.getImageId());
        flagged = true;
    }

    public void removeFlag() {
        this.setBackgroundResource(ButtonImages.BUTTON.getImageId());
        flagged = false;
    }

    public void setRedMine() {
        this.setBackgroundResource(ButtonImages.RED_MINE.getImageId());
    }

    public void setWrongMine() {
        this.setBackgroundResource(ButtonImages.WRONG_MINE.getImageId());
    }

    public void setDefaultButton() {
        this.setBackgroundResource(ButtonImages.BUTTON.getImageId());
    }

    public void highlight() {
        this.setBackgroundResource(ButtonImages.NUMBER_ZERO.getImageId());
    }

    public void incrementNumber() {
        this.number += 1;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled() {
        disabled = true;
    }
}
