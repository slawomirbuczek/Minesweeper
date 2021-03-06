package com.pk.minesweeper.game.controllers;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pk.minesweeper.R;
import com.pk.minesweeper.client.service.GlobalRankingService;
import com.pk.minesweeper.game.animation.GlobalRankingRecycleViewAdapter;
import com.pk.minesweeper.game.levels.Level;

import java.util.Objects;


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
        int id = v.getId();
        if (id == R.id.globalRankingMediumButton) {
            level = Level.MEDIUM;
        } else if (id == R.id.globalRankingHardButton) {
            level = Level.HARD;
        }

        adapter.swapRanking(GlobalRankingService.getRanking(level, this));

        runLayoutAnimation();
    }

    private void runLayoutAnimation() {
        final LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(
                this, R.anim.layout_animation_fall_down
        );
        recyclerView.setLayoutAnimation(animation);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
