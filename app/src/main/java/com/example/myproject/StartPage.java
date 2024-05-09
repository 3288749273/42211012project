package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartPage extends AppCompatActivity {

    private static int SPLASH_DISPLAY_LENGTH = 3000; // 3 seconds
    private TextView countdownTextView;
    private Button skipButton;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

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
                    // 倒计时结束，跳转到主页面
                    launchHomeActivity();
                }
            }
        };

        // 设置跳过按钮的点击事件
        skipButton.setOnClickListener(v -> {
            // 移除所有的回调，防止倒计时再次触发
            handler.removeCallbacks(runnable);
            // 跳转到主页面
            launchHomeActivity();
        });

        // 初始化倒计时文本
        countdownTextView.setText(String.valueOf(SPLASH_DISPLAY_LENGTH / 1000));
        // 开始倒计时
        handler.postDelayed(runnable, 1000);
    }

    private void launchHomeActivity() {
        Intent intent = new Intent(StartPage.this, RegisterActivity.class);
        startActivity(intent);
        finish(); // 关闭当前的SplashActivity
    }
}