package com.minesweeper.v2.board;

import android.content.Context;

import com.minesweeper.v2.controllers.GameController;
import com.minesweeper.v2.controllers.RankingController;
import com.minesweeper.v2.database.DatabaseRanking;
import com.minesweeper.v2.labels.MinesCounter;
import com.minesweeper.v2.labels.Timer;
import com.minesweeper.v2.levels.Level;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

public class Game {

    private List<BoardButton> boardButtons;
    private int numberOfMines;
    private Timer timer;
    private MinesCounter minesCounter;
    private GameStatus gameStatus;
    private List<BoardButton> highlightedButtons;

    public Game(List<BoardButton> boardButtons, Timer timer, MinesCounter minesCounter) {
        this.boardButtons = boardButtons;
        this.timer = timer;
        this.minesCounter = minesCounter;
        numberOfMines = boardButtons.size() * Level.getCurrentLevel().getMinesToBoardRatio() / 100;
        gameStatus = GameStatus.NOT_STARTED;
        highlightedButtons = new ArrayList<>();
    }

    public void onLongClickEventHandler(BoardButton button) {

        if (button.isDisabled()) {
            return;
        }

        if (gameStatus == GameStatus.NOT_STARTED) {
            firstClick(button);
        }

        if (button.isFlagged()) {
            removeFlag(button);
        } else {
            setFlag(button);
        }

    }

    public void onTouchActionDownEventHandler(BoardButton button) {

        if (button.isDisabled()) {
            highlightButtonsAround(button);
        }

    }

    private void highlightButtonsAround(BoardButton button) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                findButton(button.getNumberInRow() + i, button.getNumberInColumn() + j).ifPresent(
                        bttn -> {

                            if (bttn.isDisabled()) {
                                return;
                            }

                            if (!bttn.isFlagged()) {
                                bttn.highlight();
                            }

                            highlightedButtons.add(bttn);
                        }
                );

            }
        }
    }

    public GameStatus onTouchActionUpEventHandler(BoardButton button) {

        if (gameStatus == GameStatus.NOT_STARTED) {
            firstClick(button);
        }

        if (!highlightedButtons.isEmpty()) {

            if (highlightedButtons.stream().filter(BoardButton::isFlagged).count() == button.getNumber()) {
                highlightedButtons.stream().filter(bttn -> !bttn.isFlagged()).forEach(this::openField);
            } else {
                highlightedButtons.stream().filter(bttn -> !bttn.isFlagged()).forEach(BoardButton::setDefaultButton);
            }

            highlightedButtons.clear();
            return gameStatus;
        }


        if (!button.isFlagged() && !button.isDisabled()) {
            openField(button);
        }

        checkGameStatus();

        return gameStatus;
    }

    public void killGame() {
        timer.reset();
    }

    public void addGameToDatabase(Context context) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String date = dateFormat.format(new Date());

        int time = timer.getTime();

        String level = Level.getCurrentLevel().name();

        DatabaseRanking databaseRanking = new DatabaseRanking(context);
        databaseRanking.addSet(date, time, level);
    }

    private void firstClick(BoardButton button) {
        setMines(button);
        setNumbers();
        minesCounter.setMinesLeft(numberOfMines);
        timer.start();
        gameStatus = GameStatus.IN_PROGRESS;
    }

    private void checkGameStatus() {
        long fieldOpened = boardButtons.stream().filter(BoardButton::isDisabled).count();

        if (boardButtons.size() - fieldOpened == numberOfMines) {
            gameWon();
        }
    }

    private void gameWon() {
        timer.stop();
        boardButtons.forEach(BoardButton::setDisabled);
        boardButtons.stream().filter(bttn -> bttn.getNumber() == 9).forEach(BoardButton::setFlag);
        gameStatus = GameStatus.WON;
    }

    private void gameLost(BoardButton button) {
        timer.stop();
        boardButtons.forEach(BoardButton::setDisabled);
        boardButtons.stream().filter(bttn -> bttn.getNumber() == 9).forEach(BoardButton::changeImage);
        boardButtons.stream().filter(bttn -> bttn.isFlagged() && bttn.getNumber() != 9).forEach(BoardButton::setWrongMine);
        button.setRedMine();
        gameStatus = GameStatus.LOST;
    }

    private void openField(BoardButton button) {
        button.setDisabled();
        button.changeImage();

        if (button.getNumber() == 9) {
            gameLost(button);
        } else if (button.getNumber() == 0) {
            openAreaAroundButton(button);
        }
    }

    private void openAreaAroundButton(BoardButton button) {
        int row = button.getNumberInRow();
        int column = button.getNumberInColumn();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                Optional<BoardButton> optionalBoardButton = findButton(row + i, column + j);

                if (optionalBoardButton.isPresent()) {

                    BoardButton boardButton = optionalBoardButton.get();

                    if (boardButton.isDisabled()) {
                        continue;
                    } else if (boardButton.isFlagged()) {
                        removeFlag(boardButton);
                    }

                    openField(boardButton);

                }

            }
        }
    }

    private void setFlag(BoardButton button) {
        button.setFlag();
        minesCounter.decrement();
    }

    private void removeFlag(BoardButton button) {
        button.removeFlag();
        minesCounter.increment();
    }

    private Optional<BoardButton> findButton(int row, int column) {
        return boardButtons.stream()
                .filter(bttn -> bttn.getNumberInRow() == row && bttn.getNumberInColumn() == column)
                .findAny();
    }

    private void setMines(BoardButton firstButton) {
        Random random = new Random();

        for (int i = 0; i < numberOfMines; i++) {

            BoardButton randomButton = boardButtons.get(random.nextInt(boardButtons.size()));

            if (randomButton.getNumber() == 9 || randomButton == firstButton) {
                i--;
            } else {
                randomButton.setNumber(9);
            }

        }
    }

    private void setNumbers() {
        boardButtons.stream().filter(button -> button.getNumber() == 9).forEach(this::incrementButtonsNumberAroundMine);
    }

    private void incrementButtonsNumberAroundMine(BoardButton button) {
        int row = button.getNumberInRow();
        int column = button.getNumberInColumn();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                findButton(row + i, column + j).ifPresent(
                        bttn -> {
                            if (bttn.getNumber() != 9) {
                                bttn.incrementNumber();
                            }
                        }
                );

            }
        }

    }
}
