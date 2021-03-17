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
        findViewById(R.id.menuStatisticsButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.menuStartButton) {
            startActivity(new Intent(this, LevelSelectController.class));
        } else if (id == R.id.menuLocalRankingButton) {
            startActivity(new Intent(this, LocalRankingController.class));
        } else if (id == R.id.menuGlobalRankingButton) {
            startActivity(new Intent(this, GlobalRankingController.class));
        } else if (id == R.id.menuLogoutButton) {
            startActivity(new Intent(this, LoginController.class));
        } else if (id == R.id.menuStatisticsButton) {
            startActivity(new Intent(this, StatisticsController.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
