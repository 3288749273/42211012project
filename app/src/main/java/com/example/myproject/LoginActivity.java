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
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextID.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (databaseHelper.checkUser(id, password)) {
                    User user = databaseHelper.getUserById(id);
                    saveLoginState(user.getId(), user.getIdentity());
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeMenu.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败，请检查账号和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveLoginState(String userId, String identity) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userId); // 保存用户ID
        editor.putString("user_identity", identity); // 保存用户身份
        editor.putBoolean("is_logged_in", true); // 保存登录状态
        editor.apply();
    }
}
