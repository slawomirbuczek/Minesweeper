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
import com.minesweeper.v2.animation.RecycleViewAdapter;
import com.minesweeper.v2.levels.Level;

public class LocalRankingController extends AppCompatActivity implements View.OnClickListener {

    DatabaseRanking databaseRanking;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_ranking);
        findViewById(R.id.globalRankingEasyButton).setOnClickListener(this);
        findViewById(R.id.globalRankingMediumButton).setOnClickListener(this);
        findViewById(R.id.globalRankingHardButton).setOnClickListener(this);

        recyclerView = findViewById(R.id.localRankingRecycleView);

        databaseRanking = new DatabaseRanking(this);
        databaseRanking.open();

        adapter = new RecycleViewAdapter(this, databaseRanking.getRanking(Level.EASY));

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
