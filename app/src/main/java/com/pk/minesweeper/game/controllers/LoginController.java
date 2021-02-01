package com.pk.minesweeper.game.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pk.minesweeper.R;
import com.pk.minesweeper.client.service.LoginService;
import com.pk.minesweeper.client.service.Preferences;

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
    public void onResume() {
        super.onResume();

        String username = Preferences.getUsername(this);
        String password = Preferences.getPassword(this);

        if (username != null) {
            etUsername.setText(username);
            etPassword.setText(password);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Preferences.addUsername(etUsername.getText().toString(), this);
        Preferences.addPassword(etPassword.getText().toString(), this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String result = LoginService.login(username, password, this);
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                if (result.contains("successfully")) {
                    startActivity(new Intent(this, MenuController.class));
                }
                break;
            case R.id.login_register_button:
                startActivity(new Intent(this, RegisterController.class));
                break;
        }
    }

}
