package com.neizathedev.photovideoapplimegig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import android.os.Bundle;

import com.neizathedev.photovideoapplimegig.Authentication.LoginActivity;

/**
 * @Author: Monei Bakang Mothuti
 * @Date: Wednesday July 2023
 * @Time: 9:29 PM
 */


public class SplashScreenActivity extends AppCompatActivity {
    private ImageView splashScreenImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}