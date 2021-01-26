package com.minesweeper.game.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minesweeper.R;
import com.minesweeper.client.service.RegistrationService;

public class RegisterController extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.register_button).setOnClickListener(this);
        etUsername = findViewById(R.id.register_username);
        etPassword = findViewById(R.id.register_password);

    }

    @Override
    public void onClick(View v) {

        String result = register();

        if (result.contains("successfully")) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginController.class));
        } else {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }

    }

    private String register() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        return RegistrationService.register(username, password);
    }

}