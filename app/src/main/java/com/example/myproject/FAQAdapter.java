package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class FAQAdapter extends BaseAdapter {
    private Context context;
    private List<FAQ> faqList;

    public FAQAdapter(Context context, List<FAQ> faqList) {
        this.context = context;
        this.faqList = faqList;
    }

    @Override
    public int getCount() {
        return faqList.size();
    }

    @Override
    public Object getItem(int position) {
        return faqList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_questions_item, parent, false);
        }

        TextView questionTextView = convertView.findViewById(R.id.questionTextView);
        TextView answerTextView = convertView.findViewById(R.id.answerTextView);

        FAQ faq = faqList.get(position);
        questionTextView.setText(faq.getQuestion());
        answerTextView.setText(faq.getAnswer());

        if (faq.isAnswerVisible()) {
            answerTextView.setVisibility(View.VISIBLE);
        } else {
            answerTextView.setVisibility(View.GONE);
        }

        questionTextView.setOnClickListener(v -> {
            faq.setAnswerVisible(!faq.isAnswerVisible());
            notifyDataSetChanged();
        });

        return convertView;
    }
}
