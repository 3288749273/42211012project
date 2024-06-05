package com.example.myproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ManageAdminActivity extends AppCompatActivity {

    private Button buttonAddAdmin;
    private Button buttonDeleteAdmin;
    private Button buttonCheckAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admin);
        buttonAddAdmin = findViewById(R.id.buttonAddAdmin);
        buttonDeleteAdmin = findViewById(R.id.buttonDeleteAdmin);
        buttonCheckAdmin = findViewById(R.id.buttonCheckAdmin);

        buttonAddAdmin.setOnClickListener(v -> showAdminDialog(com.example.myproject.AdminTask.ADD_ADMIN));
        buttonDeleteAdmin.setOnClickListener(v -> showAdminDialog(AdminTask.DELETE_ADMIN));
        buttonCheckAdmin.setOnClickListener(v -> showAdminDialog(AdminTask.CHECK_ADMIN));
    }

    private void showAdminDialog(int taskType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(taskType == AdminTask.ADD_ADMIN ? "添加管理员" :
                taskType == AdminTask.DELETE_ADMIN ? "删除管理员" : "验证管理员");

        View view = getLayoutInflater().inflate(R.layout.activity_admin_login, null);
        final EditText editTextDialogAdminID = view.findViewById(R.id.editTextAdminID);
        final EditText editTextDialogAdminPassword = view.findViewById(R.id.editTextAdminPassword);

        if (taskType == AdminTask.CHECK_ADMIN) {
            editTextDialogAdminPassword.setVisibility(View.VISIBLE);
        } else {
            editTextDialogAdminPassword.setVisibility(View.GONE);
        }

        builder.setView(view);

        builder.setPositiveButton("确定", (dialog, which) -> {
            String adminID = editTextDialogAdminID.getText().toString().trim();
            String adminPassword = editTextDialogAdminPassword.getText().toString().trim();
            if (!adminID.isEmpty()) {
                new AdminTask(ManageAdminActivity.this, taskType, adminID, adminPassword).execute();
            } else {
                Toast.makeText(ManageAdminActivity.this, "请输入管理员账号", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", null);

        builder.create().show();
    }
}
