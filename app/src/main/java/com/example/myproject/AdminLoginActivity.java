package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText editTextAdminID;
    private EditText editTextAdminPassword;
    private Button buttonLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        databaseHelper = new DatabaseHelper(this);
        editTextAdminID = findViewById(R.id.editTextAdminID);
        editTextAdminPassword = findViewById(R.id.editTextAdminPassword);
        buttonLogin = findViewById(R.id.buttonAdminLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminID = editTextAdminID.getText().toString().trim();
                String adminPassword = editTextAdminPassword.getText().toString().trim();

                if (databaseHelper.checkAdminUser(adminID, adminPassword)) {
                    Intent intent = new Intent(AdminLoginActivity.this, ManageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AdminLoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
