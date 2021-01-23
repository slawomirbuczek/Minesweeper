package com.minesweeper.v2.controllers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.minesweeper.R;
import com.minesweeper.v2.animation.RecycleViewAdapter;
import com.minesweeper.v2.client.service.LoginService;

public class GlobalRankingController extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private LoginService loginService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_ranking);

    }
}
