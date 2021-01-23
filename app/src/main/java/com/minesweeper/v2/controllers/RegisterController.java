package com.minesweeper.v2.controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minesweeper.R;

public class RegisterController extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.register_button);
        etUsername = findViewById(R.id.register_username);
        etPassword = findViewById(R.id.register_password);

    }

    @Override
    public void onClick(View v) {

    }
}