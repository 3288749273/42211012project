package com.example.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextID;
    private EditText editTextPassword;
    private Button buttonLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();
    }
    private void initData() {
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
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeMenu.class);
                    startActivity(intent);
                    finish(); // 结束当前的登录页面
                } else {
                    // 登录失败，显示提示信息
                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void saveLoginState(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userId); // 保存用户ID
        editor.putBoolean("is_logged_in", true); // 保存登录状态
        editor.apply();
    }
}

