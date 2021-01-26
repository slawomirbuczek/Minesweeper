package com.minesweeper.game.controllers;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minesweeper.R;
import com.minesweeper.client.service.GlobalRankingService;
import com.minesweeper.game.animation.GlobalRankingRecycleViewAdapter;
import com.minesweeper.game.levels.Level;

public class GlobalRankingController extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private GlobalRankingRecycleViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_ranking);
        findViewById(R.id.globalRankingEasyButton).setOnClickListener(this);
        findViewById(R.id.globalRankingMediumButton).setOnClickListener(this);
        findViewById(R.id.globalRankingHardButton).setOnClickListener(this);

        recyclerView = findViewById(R.id.globalRankingRecycleView);

        adapter = new GlobalRankingRecycleViewAdapter(this, GlobalRankingService.getRanking(Level.EASY, this));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        Level level = Level.EASY;
        switch (v.getId()) {
            case R.id.globalRankingMediumButton:
                level = Level.MEDIUM;
                break;
            case R.id.globalRankingHardButton:
                level = Level.HARD;
                break;
        }

        adapter.swapRanking(GlobalRankingService.getRanking(level, this));

        runLayoutAnimation();
    }

    private void runLayoutAnimation() {
        final LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(
                this, R.anim.layout_animation_fall_down
        );
        recyclerView.setLayoutAnimation(animation);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
