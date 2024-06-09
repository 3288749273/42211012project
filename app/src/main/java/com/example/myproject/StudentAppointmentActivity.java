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

public class StudentAppointmentActivity extends AppCompatActivity {

    private EditText editTextIssue, editTextStudentID, editTextPhone, editTextRepairTime;
    private Button buttonSubmitStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_student);

        editTextIssue = findViewById(R.id.editTextIssue);
        editTextStudentID = findViewById(R.id.editTextStudentID);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextRepairTime = findViewById(R.id.editTextRepairTime);
        buttonSubmitStudent = findViewById(R.id.buttonSubmitStudent);

        buttonSubmitStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String issue = editTextIssue.getText().toString().trim();
                String studentID = editTextStudentID.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String repairTime = editTextRepairTime.getText().toString().trim();

                if (!issue.isEmpty() && !studentID.isEmpty() && !phone.isEmpty() && !repairTime.isEmpty()) {
                    new SubmitStudentAppointmentTask().execute(issue, studentID, phone, repairTime);
                } else {
                    Toast.makeText(StudentAppointmentActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class SubmitStudentAppointmentTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String issue = params[0];
            String studentID = params[1];
            String phone = params[2];
            String repairTime = params[3];

            try {
                URL url = new URL("http://your_server_url/submit_student_appointment.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String data = URLEncoder.encode("issue", "UTF-8") + "=" + URLEncoder.encode(issue, "UTF-8") + "&" +
                        URLEncoder.encode("studentID", "UTF-8") + "=" + URLEncoder.encode(studentID, "UTF-8") + "&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&" +
                        URLEncoder.encode("repairTime", "UTF-8") + "=" + URLEncoder.encode(repairTime, "UTF-8");

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
            Toast.makeText(StudentAppointmentActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
