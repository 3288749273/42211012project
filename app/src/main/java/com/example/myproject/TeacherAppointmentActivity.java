package com.example.myproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TeacherAppointmentActivity extends AppCompatActivity {

    private EditText editTextIssue, editTextRepairTime, editTextOffice, editTextName, editTextPhone;
    private Button buttonSubmitTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_teacher);

        editTextIssue = findViewById(R.id.editTextIssue);
        editTextRepairTime = findViewById(R.id.editTextRepairTime);
        editTextOffice = findViewById(R.id.editTextOffice);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonSubmitTeacher = findViewById(R.id.buttonSubmitTeacher);

        buttonSubmitTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String issue = editTextIssue.getText().toString().trim();
                String repairTime = editTextRepairTime.getText().toString().trim();
                String office = editTextOffice.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();

                if (!issue.isEmpty() && !repairTime.isEmpty() && !office.isEmpty() && !name.isEmpty() && !phone.isEmpty()) {
                    new SubmitTeacherAppointmentTask().execute(issue, repairTime, office, name, phone);
                } else {
                    Toast.makeText(TeacherAppointmentActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class SubmitTeacherAppointmentTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String issue = params[0];
            String repairTime = params[1];
            String office = params[2];
            String name = params[3];
            String phone = params[4];

            try {
                URL url = new URL("http://your_server_url/submit_teacher_appointment.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String data = URLEncoder.encode("issue", "UTF-8") + "=" + URLEncoder.encode(issue, "UTF-8") + "&" +
                        URLEncoder.encode("repairTime", "UTF-8") + "=" + URLEncoder.encode(repairTime, "UTF-8") + "&" +
                        URLEncoder.encode("office", "UTF-8") + "=" + URLEncoder.encode(office, "UTF-8") + "&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(data);
                writer.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "预约提交成功";
                } else {
                    return "预约提交失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "网络错误";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(TeacherAppointmentActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
