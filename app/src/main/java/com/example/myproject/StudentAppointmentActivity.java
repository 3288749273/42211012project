package com.example.myproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StudentAppointmentActivity extends AppCompatActivity {

    private EditText editTextIssue, editTextStudentID, editTextPhone, editTextRepairTime;
    private Button buttonSubmitStudent;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_student);

        databaseHelper = new DatabaseHelper(this);

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

                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                String userId = sharedPreferences.getString("user_id", null);

                if (userId != null && !issue.isEmpty() && !studentID.isEmpty() && !phone.isEmpty() && !repairTime.isEmpty()) {
                    boolean isInserted = databaseHelper.addStudentAppointment(userId, issue, repairTime, studentID, phone);
                    if (isInserted) {
                        Toast.makeText(StudentAppointmentActivity.this, "预约提交成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentAppointmentActivity.this, "预约提交失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StudentAppointmentActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
