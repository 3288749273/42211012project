package com.example.myproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherAppointmentActivity extends AppCompatActivity {

    private EditText editTextIssue, editTextRepairTime, editTextOffice, editTextName, editTextPhone;
    private Button buttonSubmitTeacher;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_teacher);

        databaseHelper = new DatabaseHelper(this);

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

                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                String userId = sharedPreferences.getString("user_id", null);

                if (userId != null && !issue.isEmpty() && !repairTime.isEmpty() && !office.isEmpty() && !name.isEmpty() && !phone.isEmpty()) {
                    boolean isInserted = databaseHelper.addTeacherAppointment(userId, issue, repairTime, office, name, phone);
                    if (isInserted) {
                        Toast.makeText(TeacherAppointmentActivity.this, "预约提交成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TeacherAppointmentActivity.this, "预约提交失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TeacherAppointmentActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
