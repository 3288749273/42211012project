package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextID;
    private EditText editTextPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        editTextID = findViewById(R.id.editTextID);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextID.getText().toString().trim();
                String password = editTextPassword.getText().toString();
                // 检查用户输入的学号/工号和密码是否匹配数据库中的记录
                boolean isValid = databaseHelper.checkUser(id, password);

                if (isValid) {
                    // 登录成功，跳转到主页
                    Intent intent = new Intent(LoginActivity.this, HomeMenu.class);
                    startActivity(intent);
                    finish(); // 结束当前的登录页面
                } else {
                    // 登录失败，显示提示信息
                    Toast.makeText(LoginActivity.this, "学号/工号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

// 添加点击注册按钮跳转到HomePageActivity的逻辑
        Button btnRegister = findViewById(R.id.buttonLogin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册页面
                Intent intent = new Intent(LoginActivity.this, HomeMenu.class);
                startActivity(intent);
            }
        });

    }
}