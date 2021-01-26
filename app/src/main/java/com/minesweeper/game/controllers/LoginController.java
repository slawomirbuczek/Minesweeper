package com.minesweeper.game.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minesweeper.R;
import com.minesweeper.client.service.LoginService;

public class LoginController extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

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
        if (!getIntent().getBooleanExtra("logout", false)) {
            loadPreferences();
            getIntent().removeExtra("logout");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    public void onClick(View v) {

        if (!internetIsConnected()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
            return;
        }

        switch (v.getId()) {
            case R.id.login_button:
                if (tryToLogin()) {
                    Toast.makeText(this, "Logged in successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MenuController.class));
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.login_register_button:
                startActivity(new Intent(this, RegisterController.class));
                break;
        }
    }

    private boolean tryToLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        return LoginService.login(username, password);
    }

    private boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_UNAME, etUsername.getText().toString());
        editor.putString(PREF_PASSWORD, etPassword.getText().toString());
        editor.apply();
    }

    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        etUsername.setText(settings.getString(PREF_UNAME, ""));
        etPassword.setText(settings.getString(PREF_PASSWORD, ""));

        if (!etUsername.getText().toString().equals("")) {
            findViewById(R.id.login_button).performClick();
        }
    }
}
