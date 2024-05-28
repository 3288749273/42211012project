package com.example.myproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FAQActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private SimpleCursorAdapter adapter;
    private ListView listViewFAQs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_questions);

        db = new DatabaseHelper(this);
        listViewFAQs = findViewById(R.id.listViewFAQs);

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

    private void loadFAQs() {
        Cursor cursor = db.getAllFAQs();
        String[] from = {DatabaseHelper.COLUMN_QUESTION, DatabaseHelper.COLUMN_ANSWER};
        int[] to = {R.id.questionTextView, R.id.answerTextView};

        adapter = new SimpleCursorAdapter(this, R.layout.home_questions_item, cursor, from, to, 0);
        listViewFAQs.setAdapter(adapter);
    }
}
