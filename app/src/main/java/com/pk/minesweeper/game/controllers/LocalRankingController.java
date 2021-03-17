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
import com.pk.minesweeper.game.animation.LocalRankingRecycleViewAdapter;
import com.pk.minesweeper.game.database.DatabaseRanking;
import com.pk.minesweeper.game.levels.Level;

import java.util.Objects;

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
        int id = v.getId();
        if (id == R.id.localRankingEasyButton) {
            adapter.swapCursor(databaseRanking.getRanking(Level.EASY));
        } else if (id == R.id.localRankingMediumButton) {
            adapter.swapCursor(databaseRanking.getRanking(Level.MEDIUM));
        } else if (id == R.id.localRankingHardButton) {
            adapter.swapCursor(databaseRanking.getRanking(Level.HARD));
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
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
