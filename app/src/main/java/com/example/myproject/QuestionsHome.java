package com.example.myproject;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionsHome extends AppCompatActivity {

    private DatabaseHelper db;
    private SimpleCursorAdapter adapter;
    private ListView listViewFAQs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq); // 请确保这个布局文件存在

        db = new DatabaseHelper(this);
        listViewFAQs = findViewById(R.id.listViewFAQs);

        Button buttonAddFAQ = findViewById(R.id.buttonAddFAQ);
        buttonAddFAQ.setOnClickListener(v -> showAddFAQDialog());

        listViewFAQs.setOnItemClickListener((parent, view, position, id) -> {
            TextView answerTextView = view.findViewById(R.id.answerTextView);
            if (answerTextView.getVisibility() == View.GONE) {
                answerTextView.setVisibility(View.VISIBLE);
            } else {
                answerTextView.setVisibility(View.GONE);
            }
        });

        loadFAQs();
    }

    private void showAddFAQDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add FAQ");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_faq, null);
        final EditText editTextQuestion = view.findViewById(R.id.editTextQuestion);
        final EditText editTextAnswer = view.findViewById(R.id.editTextAnswer);

        builder.setView(view);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String question = editTextQuestion.getText().toString().trim();
            String answer = editTextAnswer.getText().toString().trim();
            if (!question.isEmpty() && !answer.isEmpty()) {
                db.addFAQ(question, answer);
                loadFAQs();
            } else {
                Toast.makeText(QuestionsHome.this, "Please enter both question and answer", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private void loadFAQs() {
        Cursor cursor = db.getAllFAQs();
        String[] from = {DatabaseHelper.COLUMN_QUESTION, DatabaseHelper.COLUMN_ANSWER};
        int[] to = {R.id.questionTextView, R.id.answerTextView};

        adapter = new SimpleCursorAdapter(this, R.layout.home_questions_item, cursor, from, to, 0);
        listViewFAQs.setAdapter(adapter);
    }
}
