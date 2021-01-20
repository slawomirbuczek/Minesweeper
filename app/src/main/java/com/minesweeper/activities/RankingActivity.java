package com.minesweeper.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minesweeper.R;
import com.minesweeper.logic.Music;
import com.minesweeper.logic.database.DatabaseRanking;
import com.minesweeper.logic.database.RecycleViewAdapter;

public class RankingActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseRanking databaseRanking;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        findViewById(R.id.rankingEasyButton).setOnClickListener(this);
        findViewById(R.id.rankingMediumButton).setOnClickListener(this);
        findViewById(R.id.rankingHardButton).setOnClickListener(this);
        recyclerView = findViewById(R.id.recycleView);

        databaseRanking = new DatabaseRanking(this);
        databaseRanking.open();

        adapter = new RecycleViewAdapter(this, databaseRanking.getRanking("easy"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(
                this, resId
        );
        recyclerView.setLayoutAnimation(animation);
    }

    private void runLayoutAnimation() {
        final LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(
                this, R.anim.layout_animation_fall_down
        );
        recyclerView.setLayoutAnimation(animation);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rankingEasyButton:
                adapter.swapCursor(databaseRanking.getRanking("easy"));
                break;
            case R.id.rankingMediumButton:
                adapter.swapCursor(databaseRanking.getRanking("medium"));
                break;
            case R.id.rankingHardButton:
                adapter.swapCursor(databaseRanking.getRanking("hard"));
                break;
        }
        runLayoutAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Music.resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseRanking.close();
    }
}
