package com.example.myproject;

import android.os.AsyncTask;
import android.widget.Toast;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminTask extends AsyncTask<String, Void, Boolean> {

    public static final int ADD_ADMIN = 1;
    public static final int DELETE_ADMIN = 2;
    public static final int CHECK_ADMIN = 3;

    private int taskType;
    private String adminID;
    private String adminPassword;
    private ManageAdminActivity activity;

    public AdminTask(ManageAdminActivity activity, int taskType, String adminID, String adminPassword) {
        this.activity = activity;
        this.taskType = taskType;
        this.adminID = adminID;
        this.adminPassword = adminPassword;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            URL url = new URL("http://localhost:8080/yixiuproject/AdminServlet");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            String jsonInputString;
            if (taskType == ADD_ADMIN) {
                conn.setRequestMethod("POST");
                jsonInputString = "{\"id\": \"" + adminID + "\"}";
            } else if (taskType == DELETE_ADMIN) {
                conn.setRequestMethod("DELETE");
                jsonInputString = "{\"id\": \"" + adminID + "\"}";
            } else {
                conn.setRequestMethod("GET");
                jsonInputString = "{\"id\": \"" + adminID + "\", \"password\": \"" + adminPassword + "\"}";
            }

            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            return (taskType == ADD_ADMIN && responseCode == HttpURLConnection.HTTP_CREATED) ||
                    (taskType == DELETE_ADMIN && responseCode == HttpURLConnection.HTTP_OK) ||
                    (taskType == CHECK_ADMIN && responseCode == HttpURLConnection.HTTP_OK);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        String message;
        if (taskType == ADD_ADMIN) {
            message = success ? "管理员添加成功" : "添加管理员失败";
        } else if (taskType == DELETE_ADMIN) {
            message = success ? "管理员删除成功" : "删除管理员失败";
        } else {
            message = success ? "管理员验证成功" : "管理员验证失败";
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
