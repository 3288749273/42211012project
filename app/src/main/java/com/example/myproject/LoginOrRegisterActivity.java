package com.example.myproject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
public class LoginOrRegisterActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnRegister, btnLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        imageView = findViewById(R.id.imageView2);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        imageView.setImageResource(R.drawable.logo);

        // 设置按钮点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册页面
                Intent intent = new Intent(LoginOrRegisterActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登录页面
                Intent intent = new Intent(LoginOrRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
