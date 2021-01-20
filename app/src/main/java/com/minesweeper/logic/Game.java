package com.minesweeper.logic;

import android.view.View;
import android.widget.ImageButton;

import com.minesweeper.R;
import com.minesweeper.logic.labels.MinesLabel;
import com.minesweeper.logic.labels.Timer;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ImageButton face;
    private final int mines;
    private int buttonsLeft;
    private final int rows;
    private final int cols;
    private final BoardButton[][] buttons;
    private MinesLabel minesLabel;
    private Timer timer;
    private boolean firstClick;
    private boolean flagMode;
    private final ArrayList<BoardButton> buttonsAround;
    private int flagsAround;
    private boolean won;

    public Game(int mines, int rows, int cols, BoardButton[][] buttons) {
        this.mines = mines;
        this.rows = rows;
        this.cols = cols;
        this.buttons = buttons;
        buttonsLeft = rows * cols - mines;
        firstClick = true;
        flagMode = false;
        buttonsAround = new ArrayList<>();
        flagsAround = 0;
        won = false;
    }

    public void onTouchActionDownEvent(View v) {
        BoardButton button = findButton(v);

        if (button.isEnabled())
            return;

        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if(k==0&&l==0)  continue;
                int x = button.getNumberInRow()+k;
                int y = button.getNumberInColumn()+l;
                if(x >= 0 && x < rows && y >= 0 && y < cols){
                   if(buttons[x][y].isEnabled())
                       if(buttons[x][y].isFlagged())
                           flagsAround++;
                       else {
                           buttonsAround.add(buttons[x][y]);
                           buttons[x][y].getButton().setBackgroundResource(R.drawable.type0);
                       }
                }
            }
        }
    }

    public void onTouchActionUpEvent(View v) {
        BoardButton button = findButton(v);

        if(!buttonsAround.isEmpty()){
            if(button.getNumber() == flagsAround){
                for (BoardButton boardButton : buttonsAround) {
                    openButton(boardButton);
                }
            }
            else{
                for (BoardButton boardButton : buttonsAround) {
                    boardButton.getButton().setBackgroundResource(R.drawable.button);
                }
            }
            buttonsAround.clear();
            flagsAround = 0;
            return;
        }

        if (firstClick) {
            placeMines(mines, rows, cols, buttons, button);
            timer.start();
            firstClick = !firstClick;
        }

        if (button.isDisabled())
            return;

        if(flagMode) {
            if (button.isFlagged())
                minesLabel.removeFlag(button);
            else
                minesLabel.setFlag(button);
            return;
        }

        if(button.isFlagged())
            return;

        openButton(button);
    }

    public void onLongClickEvent(View v){
        BoardButton button = findButton(v);

        if (button.isDisabled())
            return;

        if (firstClick) {
            placeMines(mines, rows, cols, buttons, button);
            timer.start();
            firstClick = !firstClick;
        }

        if (button.isFlagged())
            minesLabel.removeFlag(button);
        else
            minesLabel.setFlag(button);
    }

    public void restart() {
        firstClick = true;
        buttonsLeft = rows * cols - mines;
        minesLabel.resetAndSetMines(mines);
        timer.reset();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setNumber(0);
                buttons[i][j].setEnabled();
                buttons[i][j].getButton().setBackgroundResource(R.drawable.button);
                buttons[i][j].setFlagged(false);
            }
        }


    }

    private BoardButton findButton(View v){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(v.getId() == buttons[i][j].getId()) {
                    return buttons[i][j];
                }
            }
        }
        return buttons[0][0];
    }

    private void openButton(BoardButton button){
        switch (button.getNumber()) {
            case 9:
                timer.stop();
                gameLost(button);
                return;
            case 0:
                buttonsLeft--;
                button.changeImage();
                openArea(button);
                break;
            default:
                buttonsLeft--;
                button.changeImage();
                break;
        }

        if (buttonsLeft == 0) {
            timer.stop();
            gameWon();
        }
    }

    private void openArea(BoardButton button) {
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (k == 0 && l == 0) continue;
                int x = button.getNumberInRow() + k;
                int y = button.getNumberInColumn() + l;
                if (x >= 0 && x < rows && y >= 0 && y < cols)
                    if (!buttons[x][y].isDisabled()) {
                        buttonsLeft--;
                        if(buttons[x][y].isFlagged())
                            minesLabel.removeFlag(buttons[x][y]);
                        buttons[x][y].changeImage();
                        if (buttons[x][y].getNumber() == 0)
                            openArea(buttons[x][y]);
                    }
            }
        }
    }

    private void placeMines(int mines, int rows, int cols, BoardButton[][] buttons, BoardButton button){
        Random rand = new Random();
        for(int i = 0; i < mines; i++){
            int x = rand.nextInt(rows);
            int y = rand.nextInt(cols);
            if(buttons[x][y].getNumber() != 9 && buttons[x][y] != button)
                buttons[x][y].setNumber(9);
            else i--;
        }
        setNumbers(rows, cols, buttons);
    }

    private void setNumbers(int rows, int cols, BoardButton[][] buttons) {
        int mines;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(buttons[i][j].getNumber() != 9) {
                    mines = 0;
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            if(k==0&&l==0)  continue;
                            int x = i+k;
                            int y = j+l;
                            if(x >= 0 && x < rows && y >= 0 && y < cols){
                                if(buttons[x][y].getNumber() == 9) mines++;
                            }
                        }
                    }
                    buttons[i][j].setNumber(mines);
                }
            }
        }
    }

    private void gameLost(BoardButton button){
        face.setBackgroundResource(R.drawable.face_lose);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setDisabled();
                if(buttons[i][j] == button)
                    button.getButton().setBackgroundResource(R.drawable.mine_red);
                else if(buttons[i][j].getNumber() == 9)
                    if(!buttons[i][j].isFlagged())
                        buttons[i][j].getButton().setBackgroundResource(R.drawable.mine);
                else if(buttons[i][j].isFlagged() && buttons[i][j].getNumber() != 9)
                    buttons[i][j].getButton().setBackgroundResource(R.drawable.mine_wrong);
                else
                    buttons[i][j].changeImage();
            }
        }
    }

    private void gameWon() {
        face.setBackgroundResource(R.drawable.face_win);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(!buttons[i][j].isDisabled()) {
                    buttons[i][j].getButton().setBackgroundResource(R.drawable.flag);
                    buttons[i][j].setDisabled();
                }
            }
        }
        won = true;
    }

    public void changeFlagMode() {
        flagMode = !flagMode;
    }

    public boolean isFlagMode() {
        return flagMode;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public int getTime(){
        return timer.getTime();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void resumeTimer() {
        timer.resume();
    }

    public void setMinesLabel(MinesLabel minesLabel) {
        this.minesLabel = minesLabel;
        minesLabel.resetAndSetMines(mines);
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setFace(ImageButton face) {
        this.face = face;
    }
}
