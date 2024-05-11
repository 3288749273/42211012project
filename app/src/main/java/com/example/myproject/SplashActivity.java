package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000; // 3 seconds
    private Handler handler = new Handler();
    private Runnable runnable;
    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        skipButton = findViewById(R.id.buttonSkip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginRegisterActivity();
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                launchLoginRegisterActivity();
            }
        };

        handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
    }

    private void launchLoginRegisterActivity() {
        handler.removeCallbacks(runnable); // 移除倒计时
        Intent intent = new Intent(SplashActivity.this, LoginOrRegisterActivity.class);
        startActivity(intent);
        finish(); // 关闭当前的SplashActivity
    }
}
