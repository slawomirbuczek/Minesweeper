package com.pk.minesweeper.game.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pk.minesweeper.R;
import com.pk.minesweeper.game.levels.Level;

public class LevelSelectController extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        findViewById(R.id.easyButton).setOnClickListener(this);
        findViewById(R.id.mediumButton).setOnClickListener(this);
        findViewById(R.id.hardButton).setOnClickListener(this);
        findViewById(R.id.selectLevelBackButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, GameController.class);
        intent.putExtra("width", findViewById(R.id.level_select_layout).getWidth());
        intent.putExtra("height", findViewById(R.id.level_select_layout).getHeight());

        switch (v.getId()) {
            case R.id.easyButton:
                Level.setCurrentLevel(Level.EASY);
                startActivity(intent);
                break;
            case R.id.mediumButton:
                Level.setCurrentLevel(Level.MEDIUM);
                startActivity(intent);
                break;
            case R.id.hardButton:
                Level.setCurrentLevel(Level.HARD);
                startActivity(intent);
                break;
            case R.id.selectLevelBackButton:
                startActivity(new Intent(this, MenuController.class));
                break;
        }

    }

}
