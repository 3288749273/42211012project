package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000; // 3 seconds
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        runnable = new Runnable() {
            @Override
            public void run() {
                launchLoginOrRegisterActivity();
            }
        };

        handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
    }

    private void launchLoginOrRegisterActivity() {
        handler.removeCallbacks(runnable); // 移除倒计时
        Intent intent = new Intent(SplashActivity.this, LoginOrRegisterActivity.class);
        startActivity(intent);
        finish(); // 关闭当前的SplashActivity
    }
}
