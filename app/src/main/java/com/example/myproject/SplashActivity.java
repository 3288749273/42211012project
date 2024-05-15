package com.example.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_DISPLAY_LENGTH = 3000; // 3 seconds
    private TextView countdownTextView;
    private Button skipButton;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        countdownTextView = findViewById(R.id.textCountdown);
        skipButton = findViewById(R.id.buttonSkip);

        // 初始化倒计时Runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                if (SPLASH_DISPLAY_LENGTH > 0) {
                    SPLASH_DISPLAY_LENGTH -= 1000; // 减少1秒（1000毫秒）
                    countdownTextView.setText(String.valueOf(SPLASH_DISPLAY_LENGTH / 1000));
                    // 每1000毫秒更新一次
                    handler.postDelayed(this, 1000);
                } else {
                    // 倒计时结束，跳转到相应页面
                    checkLoginStateAndLaunch();
                }
            }
        };

        // 设置跳过按钮的点击事件
        skipButton.setOnClickListener(v -> {
            // 移除所有的回调，防止倒计时再次触发
            handler.removeCallbacks(runnable);
            // 立即检查登录状态并跳转
            checkLoginStateAndLaunch();
        });

        // 初始化倒计时文本
        countdownTextView.setText(String.valueOf(SPLASH_DISPLAY_LENGTH / 1000));
        // 开始倒计时
        handler.postDelayed(runnable, 1000);
    }

    private void checkLoginStateAndLaunch() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        Log.d("SplashActivity", "isLoggedIn: " + isLoggedIn);

        Intent intent;
        if (isLoggedIn) {
            // 用户已登录，跳转到主界面
            intent = new Intent(SplashActivity.this, HomeMenu.class);
        } else {
            // 用户未登录，跳转到登录或注册界面
            intent = new Intent(SplashActivity.this, LoginOrRegisterActivity.class);
        }
        startActivity(intent);
        finish(); // 关闭当前的SplashActivity
    }
}
