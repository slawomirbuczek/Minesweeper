package com.minesweeper.logic;

import android.widget.ImageButton;

public class BoardButton {
    
    private final int id;
    private int number;
    private final ImageButton button;
    private boolean disabled;
    private final int numberInRow;
    private final int numberInColumn;
    private boolean isFlagged;

    public BoardButton(int id, ImageButton button, int numberInRow, int numberInColumn) {
        this.id = id;
        this.number = 0;
        this.button = button;
        this.numberInRow = numberInRow;
        this.numberInColumn = numberInColumn;
        this.isFlagged = false;
        this.disabled = false;
    }

    void changeImage() {
        this.disabled = true;
        getButton().setBackgroundResource(ButtonImage.getImage(this.number));
    }

    int getId() {
        return id;
    }

    int getNumber() {
        return number;
    }

    public ImageButton getButton() {
        return button;
    }

    int getNumberInRow() {
        return numberInRow;
    }

    int getNumberInColumn() {
        return numberInColumn;
    }

    boolean isDisabled() {
        return disabled;
    }

    boolean isEnabled() {
        return !disabled;
    }

    void setNumber(int number) {
        this.number = number;
    }

    void setDisabled() {
        this.disabled = true;
    }

    void setEnabled() {
        this.disabled = false;
    }

    boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
