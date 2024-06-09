package com.example.myproject;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserInfoActivity extends AppCompatActivity {

    private TextView textViewUserId;
    private TextView textViewUserName;
    private TextView textViewUserPhone;
    private TextView textViewUserIdentity;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        textViewUserId = findViewById(R.id.textViewUserId);
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewUserPhone = findViewById(R.id.textViewUserPhone);
        textViewUserIdentity = findViewById(R.id.textViewUserIdentity);
        databaseHelper = new DatabaseHelper(this);

        // 获取登录用户ID
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        if (userId != null) {
            try {
                // 从数据库中获取用户信息
                User user = databaseHelper.getUserById(userId);
                if (user != null) {
                    textViewUserId.setText("ID: " + user.getId());
                    textViewUserName.setText("Name: " + user.getName());
                    textViewUserPhone.setText("Phone: " + user.getPhone());
                    textViewUserIdentity.setText("Identity: " + user.getIdentity());
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 处理可能的异常
                textViewUserId.setText("ID: Error");
                textViewUserName.setText("Name: Error");
                textViewUserPhone.setText("Phone: Error");
                textViewUserIdentity.setText("Identity: Error");
            }
        } else {
            // 处理未找到用户ID的情况
            textViewUserId.setText("ID: Not Logged In");
            textViewUserName.setText("Name: Not Logged In");
            textViewUserPhone.setText("Phone: Not Logged In");
            textViewUserIdentity.setText("Identity: Not Logged In");
        }
    }
}
