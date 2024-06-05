package com.example.myproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;

public class ManageFAQActivity extends AppCompatActivity {

    private SimpleCursorAdapter adapter;
    private ListView listViewFAQs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_faq);

        listViewFAQs = findViewById(R.id.listViewFAQs);

        Button buttonAddFAQ = findViewById(R.id.buttonAddFAQ);
        buttonAddFAQ.setOnClickListener(v -> showAddFAQDialog());

        new LoadFAQsTask().execute();
    }

    private void showAddFAQDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加FAQ");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_faq, null);
        final EditText editTextQuestion = view.findViewById(R.id.editTextQuestion);
        final EditText editTextAnswer = view.findViewById(R.id.editTextAnswer);

        builder.setView(view);

        builder.setPositiveButton("添加", (dialog, which) -> {
            String question = editTextQuestion.getText().toString().trim();
            String answer = editTextAnswer.getText().toString().trim();
            if (!question.isEmpty() && !answer.isEmpty()) {
                new AddFAQTask().execute(question, answer);
            } else {
                Toast.makeText(ManageFAQActivity.this, "请输入问题和答案！", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", null);

        builder.create().show();
    }

    private class LoadFAQsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return HttpHelper.sendRequest("http://192.168.237.131:8080/myproject/faq", "GET", null);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    // 将数据转换为Cursor对象，并绑定到ListView
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ManageFAQActivity.this, "加载FAQ失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AddFAQTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String question = params[0];
            String answer = params[1];
            String param = "question=" + question + "&answer=" + answer;
            return HttpHelper.sendRequest("http://192.168.237.131:8080/myproject/faq", "POST", param);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.equals("created")) {
                Toast.makeText(ManageFAQActivity.this, "FAQ添加成功", Toast.LENGTH_SHORT).show();
                new LoadFAQsTask().execute();
            } else {
                Toast.makeText(ManageFAQActivity.this, "添加FAQ失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
