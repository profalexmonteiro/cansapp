package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //this.getSupportActionBar().hide();
        //getSupportActionBar().hide();
      //  this.getWindow().setStatusBarColor(new Color().parseColor("blue"));
        //getWindow().setStatusBarColor();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent ab = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(ab);
                finish();
            }
        },3000);

    }
}