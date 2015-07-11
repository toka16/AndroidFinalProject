package com.example.user.finalproject.Adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.finalproject.R;
import com.example.user.finalproject.model.As_Usual;
import com.example.user.finalproject.model.Category;

import java.util.ArrayList;

public class Category_Adapter extends BaseAdapter{


    private Context context;
    private ArrayList<Category> categories;

    public Category_Adapter(Context context , ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return this.categories.size();
    }

    @Override
    public Object getItem(int position) {
        return this.categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.as_usual_list_view_item, null);
            Holder h = new Holder();

            h.txtV = (TextView)convertView.findViewById(R.id.category_name);
            convertView.setTag(h);
        }
        Holder h = (Holder)convertView.getTag();
        Category info = this.categories.get(position);
        h.txtV.setText(info.getName());
        return convertView;
    }

    public static class Holder {
        TextView txtV;
    }
}
