package com.minesweeper.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.minesweeper.R;
import com.minesweeper.logic.Music;


public class LevelSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean switchingActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        switchingActivities = false;
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        findViewById(R.id.easyButton).setOnClickListener(this);
        findViewById(R.id.mediumButton).setOnClickListener(this);
        findViewById(R.id.hardButton).setOnClickListener(this);
        findViewById(R.id.selectLevelBackButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        switchingActivities = true;
        switch (v.getId()) {
            case R.id.easyButton:
                intent.putExtra("level", "easy");
                startActivity(intent);
                break;
            case R.id.mediumButton:
                intent.putExtra("level", "medium");
                startActivity(intent);
                break;
            case R.id.hardButton:
                intent.putExtra("level", "hard");
                startActivity(intent);
                break;
            case R.id.selectLevelBackButton:
                startActivity(new Intent(this, MenuActivity.class));
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
