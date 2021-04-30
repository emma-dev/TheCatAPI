package com.example.catapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashSreen extends AppCompatActivity {

    public static final int SPLASH_DISPLAY_LENGTH = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);
        getSupportActionBar().hide();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashSreen.this, MainActivity.class);
            SplashSreen.this.startActivity(intent);
            SplashSreen.this.finish();

        }, SPLASH_DISPLAY_LENGTH);

    }

}
