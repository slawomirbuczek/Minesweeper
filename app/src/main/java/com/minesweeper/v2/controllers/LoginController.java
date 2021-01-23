package com.minesweeper.v2.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minesweeper.R;
import com.minesweeper.v2.client.model.LoginCredentials;
import com.minesweeper.v2.client.service.LoginService;

import java.util.concurrent.ExecutionException;

public class LoginController extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.login_register_button).setOnClickListener(this);

        etUsername = findViewById(R.id.login_username);
        etPassword = findViewById(R.id.login_password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:

                LoginCredentials loginCredentials = new LoginCredentials(etUsername.getText().toString(), etPassword.getText().toString());
                LoginService loginService = new LoginService();

                try {
                    boolean result = loginService.execute(loginCredentials).get();
                    if (result) {
                        Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, GlobalRankingController.class));
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Login failed, cannot connect to the server", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.login_register_button:
                startActivity(new Intent(this, RegisterController.class));
                break;
        }
    }
}
