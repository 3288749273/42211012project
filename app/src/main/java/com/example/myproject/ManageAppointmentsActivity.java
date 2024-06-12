package com.example.myproject;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class ManageAppointmentsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listViewTeacherAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_appointments);

        databaseHelper = new DatabaseHelper(this);
        listViewTeacherAppointments = findViewById(R.id.listViewAppointments);

        loadTeacherAppointments();
    }

    private void loadTeacherAppointments() {
        Cursor cursor = databaseHelper.getAllTeacherAppointments();
        String[] from = new String[]{"issue", "repair_time", "office"};
        int[] to = new int[]{R.id.textViewIssue, R.id.textViewRepairTime, R.id.textViewOffice};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.appointment_item, cursor, from, to, 0);

        listViewTeacherAppointments.setAdapter(adapter);
    }
}
