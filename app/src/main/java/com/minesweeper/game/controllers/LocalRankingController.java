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
import com.minesweeper.game.database.DatabaseRanking;
import com.minesweeper.game.animation.LocalRankingRecycleViewAdapter;
import com.minesweeper.game.levels.Level;

public class LocalRankingController extends AppCompatActivity implements View.OnClickListener {

    DatabaseRanking databaseRanking;
    RecyclerView recyclerView;
    LocalRankingRecycleViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_ranking);
        findViewById(R.id.localRankingEasyButton).setOnClickListener(this);
        findViewById(R.id.localRankingMediumButton).setOnClickListener(this);
        findViewById(R.id.localRankingHardButton).setOnClickListener(this);

        recyclerView = findViewById(R.id.localRankingRecycleView);

        databaseRanking = new DatabaseRanking(this);
        databaseRanking.open();

        adapter = new LocalRankingRecycleViewAdapter(this, databaseRanking.getRanking(Level.EASY));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.globalRankingEasyButton:
                adapter.swapCursor(databaseRanking.getRanking(Level.EASY));
                break;
            case R.id.globalRankingMediumButton:
                adapter.swapCursor(databaseRanking.getRanking(Level.MEDIUM));
                break;
            case R.id.globalRankingHardButton:
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

    private void runLayoutAnimation() {
        final LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(
                this, R.anim.layout_animation_fall_down
        );
        recyclerView.setLayoutAnimation(animation);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
