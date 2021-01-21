package com.minesweeper.v2.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minesweeper.R;

public class MenuController extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.startButton).setOnClickListener(this);
        findViewById(R.id.rankingButton).setOnClickListener(this);
        findViewById(R.id.musicButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.startButton:
                startActivity(new Intent(this, LevelSelectController.class));
                break;
            case R.id.rankingButton:
                startActivity(new Intent(this, RankingController.class));
                break;
            case R.id.musicButton:

                break;
        }

    }
}