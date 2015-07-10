package com.example.user.finalproject.Adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.finalproject.R;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.News;

import java.util.ArrayList;

public class News_Tab_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<News> news;

    public News_Tab_Adapter(Context context, ArrayList<News> arr) {
        this.context = context;
        this.news = arr;
    }
    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.news_list_view_item, null);
            Holder h = new Holder();
            h.txtV = (TextView)convertView.findViewById(R.id.news_name);
            convertView.setTag(h);
        }
        Holder h = (Holder)convertView.getTag();
        News info = news.get(position);
        h.txtV.setText(info.getName());
        return convertView;
    }

    public static class Holder {
        TextView txtV;
    }
}
