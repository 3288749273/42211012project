package com.example.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManageActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        db = new DatabaseHelper(this);

        // 检查并添加默认管理员账号
        EditText editTextAdminID = findViewById(R.id.editTextAdminID);
        EditText editTextAdminPassword = findViewById(R.id.editTextAdminPassword);
        Button buttonAdminLogin = findViewById(R.id.buttonAdminLogin);

        buttonAdminLogin.setOnClickListener(v -> {
            String adminID = editTextAdminID.getText().toString().trim();
            String adminPassword = editTextAdminPassword.getText().toString().trim();
            if (db.checkAdminUser(adminID, adminPassword)) {
                Toast.makeText(ManageActivity.this, "管理员登录成功", Toast.LENGTH_SHORT).show();
                showManageScreen();
                saveLoginState(adminID);
            } else {
                Toast.makeText(ManageActivity.this, "登录失败，请检查账号和密码", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLoginState(String adminID) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("admin_user_id", adminID); // 保存用户ID
        editor.putBoolean("admin_is_logged_in", true); // 保存登录状态
        editor.apply();
    }

    private void showManageScreen() {
        setContentView(R.layout.activity_manage);

        Button buttonUpdateFAQ = findViewById(R.id.buttonUpdateFAQ);
        Button buttonAppointment = findViewById(R.id.buttonAppointent);

        buttonUpdateFAQ.setOnClickListener(v -> {
            Intent intent = new Intent(ManageActivity.this, ManageFAQActivity.class);
            startActivity(intent);
        });

        buttonAppointment.setOnClickListener(v -> {
            Intent intent = new Intent(ManageActivity.this, ManageAppointmentsActivity.class);
            startActivity(intent);
        });
    }
}
