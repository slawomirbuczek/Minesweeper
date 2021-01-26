package com.minesweeper.game.board;

import android.content.Context;

import com.minesweeper.client.service.GlobalRankingService;
import com.minesweeper.game.database.DatabaseRanking;
import com.minesweeper.game.labels.MinesCounter;
import com.minesweeper.game.labels.Timer;
import com.minesweeper.game.levels.Level;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;

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
            iterateAroundButton(button, highlightButtonsAround);
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

    public void resetGame() {
        timer.reset();
        boardButtons.forEach(BoardButton::reset);
        gameStatus = GameStatus.NOT_STARTED;
    }

    public void pauseTimer() {
        timer.stop();
    }

    public void resumeTimer() {
        timer.resume();
    }

    public void addGameToDatabase(Context context) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String date = dateFormat.format(new Date());

        float time = timer.getTime();

        DatabaseRanking databaseRanking = new DatabaseRanking(context);
        databaseRanking.addSet(date, time, Level.getCurrentLevel().name());

        GlobalRankingService.postRecord(time, Level.getCurrentLevel());
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
            iterateAroundButton(button, openAreaAroundButton);
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
        boardButtons.stream().filter(button -> button.getNumber() == 9).forEach(button -> iterateAroundButton(button, incrementButtonsNumberAroundMine));
    }

    private void iterateAroundButton(BoardButton button, BiConsumer<Integer, Integer> consumer) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                consumer.accept(button.getNumberInRow() + i, button.getNumberInColumn() + j);

            }
        }
    }

    private BiConsumer<Integer, Integer> highlightButtonsAround =
            (row, column) -> findButton(row, column).ifPresent(
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

    private BiConsumer<Integer, Integer> incrementButtonsNumberAroundMine =
            (row, column) -> findButton(row, column).ifPresent(
                    bttn -> {

                        if (bttn.getNumber() != 9) {
                            bttn.incrementNumber();
                        }

                    }
            );

    private BiConsumer<Integer, Integer> openAreaAroundButton =
            (row, column) -> findButton(row, column).ifPresent(
                    bttn -> {

                        if (bttn.isDisabled()) {
                            return;
                        }

                        if (bttn.isFlagged()) {
                            removeFlag(bttn);
                        }

                        openField(bttn);
                    }
            );
}
