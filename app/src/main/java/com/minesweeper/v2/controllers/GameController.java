package com.minesweeper.v2.controllers;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.minesweeper.R;
import com.minesweeper.v2.board.BoardButton;
import com.minesweeper.v2.board.Game;
import com.minesweeper.v2.board.GameStatus;
import com.minesweeper.v2.labels.MinesCounter;
import com.minesweeper.v2.labels.Timer;
import com.minesweeper.v2.levels.Level;

import java.util.ArrayList;
import java.util.List;

public class GameController extends AppCompatActivity implements View.OnTouchListener, View.OnLongClickListener {

    private Game game;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViewById(R.id.face).setOnTouchListener(this);

        initGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pauseTimer();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        game.resumeTimer();
    }

    @Override
    public boolean onLongClick(View v) {
        game.onLongClickEventHandler(findViewById(v.getId()));
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (v.getId() != R.id.face) {
                game.onTouchActionDownEventHandler(findViewById(v.getId()));
            }

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            v.performClick();

            if (v.getId() == R.id.face) {
                newGame();
            } else {
                BoardButton boardButton = findViewById(v.getId());
                GameStatus status = game.onTouchActionUpEventHandler(boardButton);
                gameStatusHandler(status);
            }

        }

        return false;
    }

    private void gameStatusHandler(GameStatus status) {

        if (status == GameStatus.WON) {
            findViewById(R.id.face).setBackgroundResource(R.drawable.face_win);
            game.addGameToDatabase(getApplicationContext());
        } else if (status == GameStatus.LOST) {
            findViewById(R.id.face).setBackgroundResource(R.drawable.face_lose);
        }

    }

    private void initGame() {
        ImageView timer_first = findViewById(R.id.timer_number_first);
        ImageView timer_second = findViewById(R.id.timer_number_second);
        ImageView timer_third = findViewById(R.id.timer_number_third);

        ImageView minesCounter_first = findViewById(R.id.mine_counter_first);
        ImageView minesCounter_second = findViewById(R.id.mine_counter_second);
        ImageView minesCounter_third = findViewById(R.id.mine_counter_third);

        List<BoardButton> boardButtons = createBoard();

        game = new Game(boardButtons,
                new Timer(timer_first, timer_second, timer_third),
                new MinesCounter(minesCounter_first, minesCounter_second, minesCounter_third));
    }

    private void newGame() {
        game.resetGame();
        findViewById(R.id.face).setBackgroundResource(R.drawable.face_unpressed);
    }

    private List<BoardButton> createBoard() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        float widthPx = displayMetrics.widthPixels;
        float heightPx = displayMetrics.heightPixels;
        float borderPx = 13 * displayMetrics.density;

        int columns = Level.getCurrentLevel().getColumns();

        int buttonSize = (int) ((widthPx - (2 * borderPx)) / columns);

        int rows = (int) ((heightPx * 0.85) / buttonSize);

        return createButtons(rows, columns, buttonSize);
    }

    private List<BoardButton> createButtons(int rows, int columns, int buttonSize) {

        List<BoardButton> boardButtons = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                boardButtons.add(addButton(row, column, buttonSize));
            }
        }
        return boardButtons;
    }

    private BoardButton addButton(int row, int column, int buttonSize) {
        ConstraintLayout parent = findViewById(R.id.board_layout);
        ConstraintSet set = new ConstraintSet();
        set.clone(parent);

        BoardButton button = new BoardButton(this);
        button.setBoardButton(row, column);
        button.setOnLongClickListener(this);
        button.setOnTouchListener(this);
        button.setHeight(buttonSize);
        button.setWidth(buttonSize);

        parent.addView(button);

        set.constrainHeight(button.getId(), buttonSize);
        set.constrainWidth(button.getId(), buttonSize);

        set.connect(button.getId(), ConstraintSet.LEFT, parent.getId(), ConstraintSet.LEFT, column * buttonSize);
        set.connect(button.getId(), ConstraintSet.BOTTOM, parent.getId(), ConstraintSet.BOTTOM, row * buttonSize);

        set.applyTo(parent);

        return button;
    }

}
