package com.example.myproject;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FAQAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private static final String SERVER_URL = "http://localhost:8080/yixiuproject/FAQServlet";
    private String action;
    private String question;
    private String answer;

    public FAQAsyncTask(String action, String question, String answer) {
        this.action = action;
        this.question = question;
        this.answer = answer;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("action", action);
            jsonParam.put("question", question);
            jsonParam.put("answer", answer);

            OutputStream os = connection.getOutputStream();
            os.write(jsonParam.toString().getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            Log.e("FAQAsyncTask", "Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.i("FAQAsyncTask", "FAQ added successfully");
        } else {
            Log.e("FAQAsyncTask", "Failed to add FAQ");
        }
    }
}
