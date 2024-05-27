package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoriesAdapter extends BaseAdapter {
    private List<Category> categoryList;
    private LayoutInflater layoutInflater;

    public CategoriesAdapter(Context context, List<Category> categoryList) {
        this.categoryList = categoryList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.menu_categories_item, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.text);
            holder.icon = convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Category category = categoryList.get(position);
        if (category != null) {
            holder.name.setText(category.getName());
            holder.icon.setImageResource(category.getIcon());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        ImageView icon;
    }
}
