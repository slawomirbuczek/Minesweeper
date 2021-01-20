package com.minesweeper.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.minesweeper.R;
import com.minesweeper.logic.Music;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean switchingActivities;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        switchingActivities = false;
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
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
                switchingActivities = true;
                startActivity(new Intent(this, LevelSelectActivity.class));
                break;
            case R.id.rankingButton:
                switchingActivities = true;
                startActivity(new Intent(this, RankingActivity.class));
                break;
            case R.id.musicButton:
                if (!Music.isInitialized()) {
                    Music.initialize(this);
                    Music.play();
                } else if (Music.isON())
                    Music.stop();
                else
                    Music.play();
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!switchingActivities)
            Music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Music.resume();
    }
}
