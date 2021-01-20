package com.minesweeper.activities;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.Glide;
import com.minesweeper.R;
import com.minesweeper.logic.BoardButton;
import com.minesweeper.logic.Game;
import com.minesweeper.logic.Music;
import com.minesweeper.logic.database.DatabaseRanking;
import com.minesweeper.logic.labels.MinesLabel;
import com.minesweeper.logic.labels.Timer;


public class GameActivity extends AppCompatActivity implements View.OnTouchListener, View.OnLongClickListener {

    ConstraintLayout constraintLayout;
    ConstraintLayout winnerLayout;
    ConstraintSet constraintSet;
    Configuration configuration;
    Game game;
    final int faceID = 999999;
    final int flagModeID = 999998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        super.onCreate(savedInstanceState);
        constraintLayout = new ConstraintLayout(this);
        constraintLayout.setId(View.generateViewId());
        constraintLayout.setBackgroundColor(Color.LTGRAY);
        setContentView(constraintLayout);
        configuration = getResources().getConfiguration();
        constraintSet = new ConstraintSet();
        createBorder();
        createBoard();
        createBar();
        constraintSet.applyTo(constraintLayout);
        initializeWinnerWindow();
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.stopTimer();
        Music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.resumeTimer();
        Music.resume();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (game.isWon())
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            game.onTouchActionDownEvent(v);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            switch (v.getId()) {
                case faceID:
                    findViewById(faceID).setBackgroundResource(R.drawable.face_unpressed);
                    game.restart();
                    break;
                case flagModeID:
                    if (game.isFlagMode()) {
                        v.setBackgroundResource(R.drawable.minetoflag);
                    } else {
                        v.setBackgroundResource(R.drawable.flagtomine);
                    }
                    game.changeFlagMode();
                    break;
                default:
                    game.onTouchActionUpEvent(v);
                    break;
            }
        }

        if (game.isWon())
            openWinnerWindow();
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        if (game.isWon())
            return true;

        game.onLongClickEvent(v);

        if (game.isWon())
            openWinnerWindow();
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createBoard() {
        Bundle bex = getIntent().getExtras();
        String level = bex.getString("level");

        int boardWidth = convertDpToPx(configuration.screenWidthDp) - 60;
        int boardHeight = convertDpToPx(configuration.screenHeightDp) - 215;
        int cols;

        switch (level) {
            case "medium":
                cols = 8;
                break;
            case "hard":
                cols = 9;
                break;
            default:
                cols = 7;
        }

        int buttonSize = boardWidth / cols;
        int rows = boardHeight / buttonSize;
        buttonSize = Math.max(boardWidth / cols, boardHeight / rows);
        int horizontalMargin = (convertDpToPx(configuration.screenWidthDp) - (buttonSize * cols)) / 2;
        int verticalMargin = (boardHeight + 25 - (buttonSize * rows)) / 2;
        ImageButton button;
        BoardButton[][] buttons = new BoardButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                button = new ImageButton(this);
                button.setOnTouchListener(this);
                button.setOnLongClickListener(this);
                button.setId(View.generateViewId());
                button.setMaxWidth(buttonSize);
                button.setMaxHeight(buttonSize);
                button.setBackgroundResource(R.drawable.button);
                buttons[i][j] = new BoardButton(button.getId(), button, i, j);
                constraintLayout.addView(button);
                constraintSet.constrainHeight(button.getId(), buttonSize);
                constraintSet.constrainWidth(button.getId(), buttonSize);
                constraintSet.connect(button.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, buttonSize * i + verticalMargin);
                constraintSet.connect(button.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, buttonSize * j + horizontalMargin);
            }
        }
        initializeGame(rows, cols, buttons);
    }

    private void createBorder() {
        for (int i = 0; i < convertDpToPx(configuration.screenHeightDp) / 600 + 1; i++) {
            ImageView borderVerticalLeft = new ImageView(this);
            borderVerticalLeft.setImageResource(R.drawable.border_vert);
            borderVerticalLeft.setId(View.generateViewId());
            constraintLayout.addView(borderVerticalLeft);
            constraintSet.constrainHeight(borderVerticalLeft.getId(), 600);
            constraintSet.constrainWidth(borderVerticalLeft.getId(), 30);
            constraintSet.connect(borderVerticalLeft.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, i * 550);
            constraintSet.connect(borderVerticalLeft.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);

            ImageView borderVerticalRight = new ImageView(this);
            borderVerticalRight.setImageResource(R.drawable.border_vert);
            borderVerticalRight.setId(View.generateViewId());
            constraintLayout.addView(borderVerticalRight);
            constraintSet.constrainHeight(borderVerticalRight.getId(), 600);
            constraintSet.constrainWidth(borderVerticalRight.getId(), 30);
            constraintSet.connect(borderVerticalRight.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, i * 550);
            constraintSet.connect(borderVerticalRight.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
        }

        for (int i = 0; i < convertDpToPx(configuration.screenWidthDp) / 600 + 1; i++) {
            ImageView borderHorizontalUpper = new ImageView(this);
            borderHorizontalUpper.setImageResource(R.drawable.border_hor);
            borderHorizontalUpper.setId(View.generateViewId());
            constraintLayout.addView(borderHorizontalUpper);
            constraintSet.constrainHeight(borderHorizontalUpper.getId(), 30);
            constraintSet.constrainWidth(borderHorizontalUpper.getId(), 600);
            constraintSet.connect(borderHorizontalUpper.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
            constraintSet.connect(borderHorizontalUpper.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, i * 550);

            ImageView borderHorizontalMiddle = new ImageView(this);
            borderHorizontalMiddle.setImageResource(R.drawable.border_hor);
            borderHorizontalMiddle.setId(View.generateViewId());
            constraintLayout.addView(borderHorizontalMiddle);
            constraintSet.constrainHeight(borderHorizontalMiddle.getId(), 30);
            constraintSet.constrainWidth(borderHorizontalMiddle.getId(), 600);
            constraintSet.connect(borderHorizontalMiddle.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 185);
            constraintSet.connect(borderHorizontalMiddle.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, i * 550);

            ImageView borderHorizontalBottom = new ImageView(this);
            borderHorizontalBottom.setImageResource(R.drawable.border_hor);
            borderHorizontalBottom.setId(View.generateViewId());
            constraintLayout.addView(borderHorizontalBottom);
            constraintSet.constrainHeight(borderHorizontalBottom.getId(), 30);
            constraintSet.constrainWidth(borderHorizontalBottom.getId(), 600);
            constraintSet.connect(borderHorizontalBottom.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(borderHorizontalBottom.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, i * 550);
        }

        ImageView cornerUpLeft = new ImageView(this);
        cornerUpLeft.setImageResource(R.drawable.corner_up_left);
        cornerUpLeft.setId(View.generateViewId());
        constraintLayout.addView(cornerUpLeft);
        constraintSet.constrainHeight(cornerUpLeft.getId(), 30);
        constraintSet.constrainWidth(cornerUpLeft.getId(), 30);
        constraintSet.connect(cornerUpLeft.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(cornerUpLeft.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);

        ImageView cornerUpRight = new ImageView(this);
        cornerUpRight.setImageResource(R.drawable.corner_up_right);
        cornerUpRight.setId(View.generateViewId());
        constraintLayout.addView(cornerUpRight);
        constraintSet.constrainHeight(cornerUpRight.getId(), 30);
        constraintSet.constrainWidth(cornerUpRight.getId(), 30);
        constraintSet.connect(cornerUpRight.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(cornerUpRight.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

        ImageView tLeft = new ImageView(this);
        tLeft.setImageResource(R.drawable.t_left);
        tLeft.setId(View.generateViewId());
        constraintLayout.addView(tLeft);
        constraintSet.constrainHeight(tLeft.getId(), 30);
        constraintSet.constrainWidth(tLeft.getId(), 30);
        constraintSet.connect(tLeft.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 185);
        constraintSet.connect(tLeft.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);

        ImageView tRight = new ImageView(this);
        tRight.setImageResource(R.drawable.t_right);
        tRight.setId(View.generateViewId());
        constraintLayout.addView(tRight);
        constraintSet.constrainHeight(tRight.getId(), 30);
        constraintSet.constrainWidth(tRight.getId(), 30);
        constraintSet.connect(tRight.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 185);
        constraintSet.connect(tRight.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

        ImageView cornerBottomLeft = new ImageView(this);
        cornerBottomLeft.setImageResource(R.drawable.corner_bottom_left);
        cornerBottomLeft.setId(View.generateViewId());
        constraintLayout.addView(cornerBottomLeft);
        constraintSet.constrainHeight(cornerBottomLeft.getId(), 30);
        constraintSet.constrainWidth(cornerBottomLeft.getId(), 30);
        constraintSet.connect(cornerBottomLeft.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(cornerBottomLeft.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);

        ImageView cornerBottomRight = new ImageView(this);
        cornerBottomRight.setImageResource(R.drawable.corner_bottom_right);
        cornerBottomRight.setId(View.generateViewId());
        constraintLayout.addView(cornerBottomRight);
        constraintSet.constrainHeight(cornerBottomRight.getId(), 30);
        constraintSet.constrainWidth(cornerBottomRight.getId(), 30);
        constraintSet.connect(cornerBottomRight.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(cornerBottomRight.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createBar() {

        ImageView minesBackground = new ImageView(this);
        minesBackground.setImageResource(R.drawable.nums_background);
        minesBackground.setId(View.generateViewId());
        constraintLayout.addView(minesBackground);
        constraintSet.constrainHeight(minesBackground.getId(), 160);
        constraintSet.constrainWidth(minesBackground.getId(), 260);
        constraintSet.connect(minesBackground.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 25);
        constraintSet.connect(minesBackground.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 30);

        ImageView minesX = new ImageView(this);
        minesX.setId(View.generateViewId());
        constraintLayout.addView(minesX);
        constraintSet.constrainHeight(minesX.getId(), 140);
        constraintSet.constrainWidth(minesX.getId(), 85);
        constraintSet.connect(minesX.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 35);
        constraintSet.connect(minesX.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 35);

        ImageView minesY = new ImageView(this);
        minesY.setId(View.generateViewId());
        constraintLayout.addView(minesY);
        constraintSet.constrainHeight(minesY.getId(), 140);
        constraintSet.constrainWidth(minesY.getId(), 85);
        constraintSet.connect(minesY.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 35);
        constraintSet.connect(minesY.getId(), ConstraintSet.LEFT, minesX.getId(), ConstraintSet.RIGHT);

        ImageView minesZ = new ImageView(this);
        minesZ.setId(View.generateViewId());
        constraintLayout.addView(minesZ);
        constraintSet.constrainHeight(minesZ.getId(), 140);
        constraintSet.constrainWidth(minesZ.getId(), 85);
        constraintSet.connect(minesZ.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 35);
        constraintSet.connect(minesZ.getId(), ConstraintSet.LEFT, minesY.getId(), ConstraintSet.RIGHT);

        game.setMinesLabel(new MinesLabel(minesX, minesY, minesZ));

        ImageButton face = new ImageButton(this);
        face.setBackgroundResource(R.drawable.face_unpressed);
        face.setId(faceID);
        face.setOnTouchListener(this);
        constraintLayout.addView(face);
        constraintSet.constrainHeight(face.getId(), 150);
        constraintSet.constrainWidth(face.getId(), 150);
        constraintSet.connect(face.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 30);
        constraintSet.connect(face.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, convertDpToPx(configuration.screenWidthDp) / 2);
        constraintSet.connect(face.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, convertDpToPx(configuration.screenWidthDp) / 2);
        game.setFace(face);

        ImageView timerBackground = new ImageView(this);
        timerBackground.setImageResource(R.drawable.nums_background);
        timerBackground.setId(View.generateViewId());
        constraintLayout.addView(timerBackground);
        constraintSet.constrainHeight(timerBackground.getId(), 160);
        constraintSet.constrainWidth(timerBackground.getId(), 260);
        constraintSet.connect(timerBackground.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 25);
        constraintSet.connect(timerBackground.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 30);

        ImageView timerZ = new ImageView(this);
        timerZ.setId(View.generateViewId());
        constraintLayout.addView(timerZ);
        constraintSet.constrainHeight(timerZ.getId(), 140);
        constraintSet.constrainWidth(timerZ.getId(), 85);
        constraintSet.connect(timerZ.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 35);
        constraintSet.connect(timerZ.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 35);

        ImageView timerY = new ImageView(this);
        timerY.setId(View.generateViewId());
        constraintLayout.addView(timerY);
        constraintSet.constrainHeight(timerY.getId(), 140);
        constraintSet.constrainWidth(timerY.getId(), 85);
        constraintSet.connect(timerY.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 35);
        constraintSet.connect(timerY.getId(), ConstraintSet.RIGHT, timerZ.getId(), ConstraintSet.LEFT);

        ImageView timerX = new ImageView(this);
        timerX.setId(View.generateViewId());
        constraintLayout.addView(timerX);
        constraintSet.constrainHeight(timerX.getId(), 140);
        constraintSet.constrainWidth(timerX.getId(), 85);
        constraintSet.connect(timerX.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 35);
        constraintSet.connect(timerX.getId(), ConstraintSet.RIGHT, timerY.getId(), ConstraintSet.LEFT);

        game.setTimer(new Timer(timerX, timerY, timerZ));

        ImageButton mineToFlag = new ImageButton(this);
        mineToFlag.setBackgroundResource(R.drawable.minetoflag);
        mineToFlag.setId(flagModeID);
        mineToFlag.setOnTouchListener(this);
        constraintLayout.addView(mineToFlag);
        constraintSet.constrainHeight(mineToFlag.getId(), 150);
        constraintSet.constrainWidth(mineToFlag.getId(), 150);
        constraintSet.connect(mineToFlag.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 30);
        constraintSet.connect(mineToFlag.getId(), ConstraintSet.RIGHT, timerBackground.getId(), ConstraintSet.LEFT, 10);
    }

    private void initializeGame(int rows, int cols, BoardButton[][] buttons) {
        Bundle bex = getIntent().getExtras();
        String level = bex.getString("level");
        int mines = rows * cols;
        switch (level) {
            case "easy":
                mines /= 10;
                break;
            case "medium":
                mines *= 15. / 100;
                break;
            case "hard":
                mines /= 5;
                break;
        }
        game = new Game(mines, rows, cols, buttons);
    }

    private void initializeWinnerWindow() {
        constraintSet = new ConstraintSet();

        winnerLayout = new ConstraintLayout(this);
        winnerLayout.setId(View.generateViewId());
        winnerLayout.setBackgroundColor(Color.TRANSPARENT);
        constraintSet.constrainHeight(winnerLayout.getId(), convertDpToPx(configuration.screenHeightDp));
        constraintSet.constrainWidth(winnerLayout.getId(), convertDpToPx(configuration.screenWidthDp));
        constraintSet.connect(winnerLayout.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(winnerLayout.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(winnerLayout.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 0);
        constraintSet.connect(winnerLayout.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 0);

        ImageView winnerGif = new ImageView(this);
        winnerGif.setId(View.generateViewId());
        constraintSet.constrainHeight(winnerGif.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(winnerGif.getId(), convertDpToPx(configuration.screenWidthDp));
        constraintSet.connect(winnerGif.getId(), ConstraintSet.TOP, winnerLayout.getId(), ConstraintSet.TOP, convertDpToPx(configuration.screenWidthDp) / 4);
        constraintSet.connect(winnerGif.getId(), ConstraintSet.LEFT, winnerLayout.getId(), ConstraintSet.LEFT, 0);
        constraintSet.connect(winnerGif.getId(), ConstraintSet.RIGHT, winnerLayout.getId(), ConstraintSet.RIGHT, 0);
        Glide.with(this).load(R.drawable.winner).into(winnerGif);
        winnerLayout.addView(winnerGif);

        final EditText winnerNickname = new EditText(this);
        winnerNickname.setId(View.generateViewId());
        winnerNickname.setHint(R.string.nickname);
        winnerNickname.setBackgroundColor(Color.DKGRAY);
        winnerNickname.setTextSize(20);
        winnerNickname.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        winnerNickname.setTextColor(Color.WHITE);
        winnerNickname.setHintTextColor(Color.LTGRAY);
        constraintSet.constrainHeight(winnerNickname.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(winnerNickname.getId(), 0);
        constraintSet.connect(winnerNickname.getId(), ConstraintSet.TOP, winnerGif.getId(), ConstraintSet.BOTTOM, 20);
        constraintSet.connect(winnerNickname.getId(), ConstraintSet.LEFT, winnerLayout.getId(), ConstraintSet.LEFT, 0);
        constraintSet.connect(winnerNickname.getId(), ConstraintSet.RIGHT, winnerLayout.getId(), ConstraintSet.RIGHT, 0);
        winnerLayout.addView(winnerNickname);

        Button winnerButton = new Button(this);
        winnerButton.setId(View.generateViewId());
        winnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = winnerNickname.getText().toString();
                text = text.trim();
                if (text.equals("")) {
                    Toast.makeText(GameActivity.this, "Type nickname", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseRanking databaseRanking = new DatabaseRanking(getApplicationContext());
                Bundle bex = getIntent().getExtras();
                databaseRanking.addSet(text, game.getTime(), bex.getString("level"));
                game.setWon(false);
                constraintLayout.removeView(winnerLayout);
            }
        });
        winnerButton.setText(R.string.done);
        winnerButton.setTextSize(20);
        constraintSet.constrainHeight(winnerButton.getId(), 150);
        constraintSet.constrainWidth(winnerButton.getId(), 0);
        constraintSet.connect(winnerButton.getId(), ConstraintSet.TOP, winnerNickname.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(winnerButton.getId(), ConstraintSet.LEFT, winnerLayout.getId(), ConstraintSet.LEFT, 0);
        constraintSet.connect(winnerButton.getId(), ConstraintSet.RIGHT, winnerLayout.getId(), ConstraintSet.RIGHT, 0);
        winnerLayout.addView(winnerButton);

        constraintSet.applyTo(winnerLayout);
    }

    private void openWinnerWindow() {
        constraintLayout.addView(winnerLayout);
    }

    private int convertDpToPx(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
