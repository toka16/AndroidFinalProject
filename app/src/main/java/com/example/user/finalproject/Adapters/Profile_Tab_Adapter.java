package com.example.user.finalproject.Adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.finalproject.R;
import com.example.user.finalproject.model.User_Information;

import java.util.ArrayList;

public class Profile_Tab_Adapter extends BaseAdapter {

    private Context context;
    // informationebis arraylist unda gadmoeces uechvlei
    private ArrayList<User_Information> information;

    public Profile_Tab_Adapter(Context context,ArrayList<User_Information> arr) {
        this.context = context;
        this.information = arr;
    }

    @Override
    public int getCount() {
        return information.size();
    }

    @Override
    public Object getItem(int position) {
        return information.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.profile_list_view_item, null);
            Holder h = new Holder();

            h.txtV = (TextView)convertView.findViewById(R.id.profile_text);
            convertView.setTag(h);
        }
        Holder h = (Holder)convertView.getTag();
        User_Information info = information.get(position);
        h.txtV.setText(info.toString());
        return convertView;
    }


    public static class Holder {
        TextView txtV;
    }
}
