package com.minesweeper.v2.controllers;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minesweeper.R;
import com.minesweeper.v2.database.DatabaseRanking;
import com.minesweeper.v2.database.RecycleViewAdapter;
import com.minesweeper.v2.levels.Level;

public class RankingController extends AppCompatActivity implements View.OnClickListener {

    DatabaseRanking databaseRanking;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        findViewById(R.id.rankingEasyButton).setOnClickListener(this);
        findViewById(R.id.rankingMediumButton).setOnClickListener(this);
        findViewById(R.id.rankingHardButton).setOnClickListener(this);

        recyclerView = findViewById(R.id.recycleView);

        databaseRanking = new DatabaseRanking(this);
        databaseRanking.open();

        adapter = new RecycleViewAdapter(this, databaseRanking.getRanking(Level.EASY));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
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
                adapter.swapCursor(databaseRanking.getRanking(Level.EASY));
                break;
            case R.id.rankingMediumButton:
                adapter.swapCursor(databaseRanking.getRanking(Level.MEDIUM));
                break;
            case R.id.rankingHardButton:
                adapter.swapCursor(databaseRanking.getRanking(Level.HARD));
                break;
        }
        runLayoutAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseRanking.close();
    }
}
