package com.pk.minesweeper.game.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pk.minesweeper.R;

public class MenuController extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.menuStartButton).setOnClickListener(this);
        findViewById(R.id.menuLocalRankingButton).setOnClickListener(this);
        findViewById(R.id.menuGlobalRankingButton).setOnClickListener(this);
        findViewById(R.id.menuLogoutButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuStartButton:
                startActivity(new Intent(this, LevelSelectController.class));
                break;
            case R.id.menuLocalRankingButton:
                startActivity(new Intent(this, LocalRankingController.class));
                break;
            case R.id.menuGlobalRankingButton:
                startActivity(new Intent(this, GlobalRankingController.class));
                break;
            case R.id.menuLogoutButton:
                startActivity(new Intent(this, LoginController.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
