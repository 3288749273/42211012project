package com.example.myproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ManageActivity extends AppCompatActivity {

    private EditText editTextAdminID, editTextAdminPassword;
    private Button buttonAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        editTextAdminID = findViewById(R.id.editTextAdminID);
        editTextAdminPassword = findViewById(R.id.editTextAdminPassword);
        buttonAdminLogin = findViewById(R.id.buttonAdminLogin);

        buttonAdminLogin.setOnClickListener(v -> {
            String adminID = editTextAdminID.getText().toString().trim();
            String adminPassword = editTextAdminPassword.getText().toString().trim();
            new AdminLoginTask(adminID, adminPassword).execute();
        });
    }

    private class AdminLoginTask extends AsyncTask<Void, Void, String> {
        private String adminID, adminPassword;

        AdminLoginTask(String adminID, String adminPassword) {
            this.adminID = adminID;
            this.adminPassword = adminPassword;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // 发送请求到服务器
            try {
                URL url = new URL("http://192.168.237.131:8080/your_project/AdminServlet");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String postParams = "action=check&id=" + adminID + "&password=" + adminPassword;
                writer.write(postParams);
                writer.flush();
                writer.close();
                os.close();

                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                is.close();

                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if ("Valid admin".equals(result)) {
                Toast.makeText(ManageActivity.this, "管理员登录成功", Toast.LENGTH_SHORT).show();
                showManageScreen();
            } else {
                Toast.makeText(ManageActivity.this, "登录失败，请检查账号和密码", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showManageScreen() {
        setContentView(R.layout.activity_manage);

        Button buttonUpdateFAQ = findViewById(R.id.buttonUpdateFAQ);
        Button buttonManageUsers = findViewById(R.id.buttonManageUsers);

        buttonUpdateFAQ.setOnClickListener(v -> {
            Intent intent = new Intent(ManageActivity.this, ManageFAQActivity.class);
            startActivity(intent);
        });

        buttonManageUsers.setOnClickListener(v -> {
            Intent intent = new Intent(ManageActivity.this, ManageAdminActivity.class);
            startActivity(intent);
        });
    }
}
